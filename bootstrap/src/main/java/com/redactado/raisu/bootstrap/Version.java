package com.redactado.raisu.bootstrap;

import org.jetbrains.annotations.NotNull;

public final class Version implements Comparable<Version> {

    private final int major;
    private final int minor;
    private final int patch;
    private final String suffix;

    private Version(int major, int minor, int patch, @NotNull String suffix) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.suffix = suffix;
    }

    @NotNull
    public static Version parse(@NotNull String version) {
        String cleanVersion = version.trim();
        String suffix = "";

        int dashIndex = cleanVersion.indexOf('-');
        if (dashIndex > 0) {
            suffix = cleanVersion.substring(dashIndex + 1);
            cleanVersion = cleanVersion.substring(0, dashIndex);
        }

        int major = 0;
        int minor = 0;
        int patch = 0;

        int firstDot = cleanVersion.indexOf('.');
        if (firstDot == -1) {
            major = parseIntSafe(cleanVersion);
        } else {
            major = parseIntSafe(cleanVersion.substring(0, firstDot));

            int secondDot = cleanVersion.indexOf('.', firstDot + 1);
            if (secondDot == -1) {
                minor = parseIntSafe(cleanVersion.substring(firstDot + 1));
            } else {
                minor = parseIntSafe(cleanVersion.substring(firstDot + 1, secondDot));
                patch = parseIntSafe(cleanVersion.substring(secondDot + 1));
            }
        }

        return new Version(major, minor, patch, suffix);
    }

    private static int parseIntSafe(@NotNull String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean isNewerThan(@NotNull Version other) {
        return this.compareTo(other) > 0;
    }

    @Override
    public int compareTo(@NotNull Version other) {
        if (this.major != other.major) {
            return Integer.compare(this.major, other.major);
        }
        if (this.minor != other.minor) {
            return Integer.compare(this.minor, other.minor);
        }
        if (this.patch != other.patch) {
            return Integer.compare(this.patch, other.patch);
        }

        if (this.suffix.isEmpty() && !other.suffix.isEmpty()) {
            return 1;
        }
        if (!this.suffix.isEmpty() && other.suffix.isEmpty()) {
            return -1;
        }

        return this.suffix.compareTo(other.suffix);
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch + (suffix.isEmpty() ? "" : "-" + suffix);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Version)) return false;
        Version other = (Version) obj;
        return major == other.major
                && minor == other.minor
                && patch == other.patch
                && suffix.equals(other.suffix);
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + patch;
        result = 31 * result + suffix.hashCode();
        return result;
    }
}
