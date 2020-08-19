package net.pearx.kpastebin.model

/**
 * The list of possible Pastebin paste privacy states.
 */
public enum class Privacy {
    /** Paste is visible for all users. */
    PUBLIC,

    /** Paste is invisible for others unless you share your paste link. */
    UNLISTED,

    /** Paste is visible only for you. */
    PRIVATE;
}