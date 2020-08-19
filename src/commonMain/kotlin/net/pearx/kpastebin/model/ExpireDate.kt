package net.pearx.kpastebin.model

public enum class ExpireDate(internal val code: String) {
    NEVER("N"),
    TEN_MINUTES("10M"),
    ONE_HOUR("1H"),
    ONE_DAY("1D"),
    ONE_WEEK("1W"),
    TWO_WEEEKS("2W"),
    ONE_MONTH("1M"),
    SIX_MONTHS("6M"),
    ONE_YEAR("1Y");

    internal companion object {
        fun forCode(code: String) = values().first { it.code == code }
    }
}