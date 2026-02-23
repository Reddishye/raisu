# Raisu — Frontend Developer Reference

This document describes everything a frontend/website developer needs to fetch, decrypt, and render a Raisu snapshot.

---

## Overview

A Minecraft server plugin uses Raisu to collect debug data, encrypt it, and upload it to a paste service. The result is a **shortcode** — a compact, opaque base64url string that bundles the provider, paste key, and encryption key together. The frontend website receives this shortcode (typically as a URL fragment) and is responsible for:

1. Decoding the shortcode
2. Fetching the encrypted payload from the paste service
3. Base64-decoding the payload
4. Decrypting the payload with AES-128-CBC
5. Deserializing the MessagePack binary
6. Rendering the snapshot

**Encryption is always applied.** There is no unencrypted mode.

---

## Shortcode format

The shortcode is a URL-safe base64 string (no padding, no separators) encoding the following binary structure:

```
Offset  Size  Content
──────  ────  ─────────────────────────────────────────────────
0       1     Provider ID byte  (0 = Pastes.dev, 1 = Hastebin)
1       1     Paste key length in bytes (N)
2       N     Paste key, UTF-8
2+N     16    Raw 128-bit AES key
```

Total binary size for a typical 10-character paste key: **28 bytes → 38 base64url characters**.

### Example

```
AApKMG80dGVBaUltFMLuvrZvxmc3j-XPWlL0dA
```

Decoded:
- Byte 0: `0x00` → Pastes.dev
- Byte 1: `0x0A` → paste key is 10 bytes long
- Bytes 2–11: paste key (e.g. `J0o0tAaIIm`)
- Bytes 12–27: 16-byte AES key

### Decoding the shortcode

```js
function decodeShortcode(shortcode) {
  const bytes = base64urlToBytes(shortcode);
  const providerId = bytes[0];
  const keyLen = bytes[1];
  const pasteKey = new TextDecoder().decode(bytes.slice(2, 2 + keyLen));
  const aesKey = bytes.slice(2 + keyLen);          // 16 bytes, use directly as AES key
  return { providerId, pasteKey, aesKey };
}

function base64urlToBytes(str) {
  const b64 = str.replace(/-/g, '+').replace(/_/g, '/');
  const padded = b64 + '='.repeat((4 - b64.length % 4) % 4);
  const binary = atob(padded);
  const bytes = new Uint8Array(binary.length);
  for (let i = 0; i < binary.length; i++) bytes[i] = binary.charCodeAt(i);
  return bytes;
}
```

---

## Step 1 — Fetch from paste service

| Provider ID | Service | Fetch URL |
|---|---|---|
| `0` | Pastes.dev | `https://api.pastes.dev/<pasteKey>` |
| `1` | Hastebin | `https://hastebin.com/raw/<pasteKey>` |

```js
const PROVIDERS = {
  0: (key) => `https://api.pastes.dev/${key}`,
  1: (key) => `https://hastebin.com/raw/${key}`,
};

async function fetchPayload(providerId, pasteKey) {
  const urlFn = PROVIDERS[providerId];
  if (!urlFn) throw new Error(`Unknown provider id: ${providerId}`);
  const res = await fetch(urlFn(pasteKey));
  if (!res.ok) throw new Error(`Fetch failed: ${res.status}`);
  return await res.text();  // returns a Base64 string
}
```

---

## Step 2 — Base64-decode

The content stored in the paste service is standard Base64. Decode it to bytes.

```js
function base64ToBytes(b64) {
  const binary = atob(b64);
  const bytes = new Uint8Array(binary.length);
  for (let i = 0; i < binary.length; i++) bytes[i] = binary.charCodeAt(i);
  return bytes;
}
```

---

## Step 3 — Decrypt

The payload format after Base64-decoding:

```
Offset  Size  Content
──────  ────  ──────────────────────────────
0       16    IV (random, generated per upload)
16      N     AES-128-CBC ciphertext (PKCS#7 padded)
```

The AES key (16 raw bytes) comes directly from the shortcode — no derivation step needed.

```js
async function decrypt(encryptedBytes, aesKeyBytes) {
  const key = await crypto.subtle.importKey(
    'raw', aesKeyBytes, { name: 'AES-CBC' }, false, ['decrypt']
  );
  const iv = encryptedBytes.slice(0, 16);
  const ciphertext = encryptedBytes.slice(16);
  const plaintext = await crypto.subtle.decrypt({ name: 'AES-CBC', iv }, key, ciphertext);
  return new Uint8Array(plaintext);
}
```

---

## Step 4 — Deserialize MessagePack

The decrypted bytes are a **MessagePack** binary. Use any MessagePack library (e.g. [`@msgpack/msgpack`](https://github.com/msgpack/msgpack-javascript)).

```js
import { decode } from '@msgpack/msgpack';

const snapshot = decode(plaintextBytes);
```

---

## Data structure

### `Snapshot`

```
{
  timestamp:     number,    // Unix epoch milliseconds
  serverVersion: string,    // e.g. "Paper 1.21.4"
  javaVersion:   string,    // e.g. "21.0.3"
  categories:    Category[]
}
```

### `Category`

```
{
  id:         string,      // unique identifier, e.g. "performance"
  name:       string,      // Adventure Component JSON (see note below)
  icon:       string,      // emoji or symbol, e.g. "📊"
  priority:   number,      // display order — sort ascending before rendering
  components: Component[]
}
```

> **`name` field:** Serialized with Adventure's `GsonComponentSerializer`. To get a plain display string, extract `JSON.parse(name).text`, or pass the JSON to an Adventure-compatible renderer.

### `Component`

Every component has a `type` discriminator and a `data` payload:

```
{
  type: string,
  data: object
}
```

---

## Component types

### `KEY_VALUE`
```
data: { key: string, value: string }
```

### `TEXT`
```
data: { content: string }
```

### `TABLE`
```
data: {
  headers: string[],
  rows:    string[][]
}
```

### `LIST`
```
data: { items: string[] }
```

### `PROGRESS_BAR`

`current` and `max` are doubles. Percentage = `(current / max) * 100`, clamped to `[0, 100]`.

```
data: { label: string, current: number, max: number }
```

### `GRAPH`

Data points are insertion-ordered — preserve order when rendering axes.

```
data: {
  title:      string,
  dataPoints: { [label: string]: number }
}
```

### `TREE`

Nodes are recursive. `children` is an empty array for leaf nodes.

```
data: { root: TreeNode }

TreeNode: { label: string, children: TreeNode[] }
```

---

## Complete pipeline

```js
import { decode } from '@msgpack/msgpack';

async function loadSnapshot(shortcode) {
  // 1. Decode the shortcode
  const { providerId, pasteKey, aesKey } = decodeShortcode(shortcode);

  // 2. Fetch from paste service
  const b64 = await fetchPayload(providerId, pasteKey);

  // 3. Base64-decode
  const encryptedBytes = base64ToBytes(b64);

  // 4. Decrypt
  const plaintextBytes = await decrypt(encryptedBytes, aesKey);

  // 5. Deserialize
  return decode(plaintextBytes);
}

// Example: shortcode is in the URL fragment
// https://yoursite.com/view#AApKMG80dGVBaUltFMLuvrZvxmc3j-XPWlL0dA
const snapshot = await loadSnapshot(window.location.hash.slice(1));

console.log(snapshot.serverVersion);  // "Paper 1.21.4"
console.log(snapshot.categories);     // Category[]
```

---

## Rendering guidelines

- Sort categories by `priority` ascending before rendering.
- Parse category `name` as Adventure JSON; fall back to rendering the raw string if parsing fails.
- For `PROGRESS_BAR`, clamp `(current / max) * 100` to the range `[0, 100]`.
- For `GRAPH`, preserve insertion order of `dataPoints` when rendering axes.
- For `TREE`, render nodes recursively; an empty `children` array means a leaf node.
- Skip unknown `type` values gracefully to remain forwards-compatible.
