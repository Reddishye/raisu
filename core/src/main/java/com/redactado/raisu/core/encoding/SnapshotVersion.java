package com.redactado.raisu.core.encoding;

/**
 * Snapshot format version constants.
 *
 * <p>The {@code version} field is the first key written in the MessagePack map for every snapshot.
 * Parsers that encounter an absent {@code version} key should treat the payload as {@link #V0}
 * (legacy format produced before versioning was introduced).
 *
 * <p>Version history:
 *
 * <ul>
 *   <li>{@code 0} (implicit, no field) — original format: {@code {timestamp, serverVersion,
 *       javaVersion, categories}}
 *   <li>{@code 1} — same schema as v0, explicit {@code version} field prepended
 * </ul>
 */
public final class SnapshotVersion {

    /** Format produced by parsers that pre-date versioning (no {@code version} key present). */
    public static final int V0 = 0;

    /** First explicitly versioned format. Schema identical to v0; field presence is the marker. */
    public static final int V1 = 1;

    /** The version written by the current {@link MessagePackEncoder}. */
    public static final int CURRENT = V1;

    private SnapshotVersion() {}
}
