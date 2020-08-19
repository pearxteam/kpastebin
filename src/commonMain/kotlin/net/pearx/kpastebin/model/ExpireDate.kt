package net.pearx.kpastebin.model

/**
 * The list of possible Pastebin paste expire durations.
 */
public enum class ExpireDate(internal val code: String) {
    /** Paste will never expire. */
    NEVER("N"),

    /** Paste will expire in 10 minutes after publication. */
    TEN_MINUTES("10M"),

    /** Paste will expire in a hour after publication. */
    ONE_HOUR("1H"),

    /** Paste will expire in a day after publication. */
    ONE_DAY("1D"),

    /** Paste will expire in a week after publication. */
    ONE_WEEK("1W"),

    /** Paste will expire in two weeks after publication. */
    TWO_WEEEKS("2W"),

    /** Paste will expire in a month after publication. */
    ONE_MONTH("1M"),

    /** Paste will expire in six months after publication. */
    SIX_MONTHS("6M"),

    /** Paste will expire in a year after publication. */
    ONE_YEAR("1Y");

    internal companion object {
        fun forCode(code: String) = values().first { it.code == code }
    }
}