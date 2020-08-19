package net.pearx.kpastebin.model

import kotlinx.serialization.*
import net.pearx.kpastebin.internal.MODEL_PACKAGE

@Serializable
enum class ExpireDate(val code: String) {
    NEVER("N"),
    TEN_MINUTES("10M"),
    ONE_HOUR("1H"),
    ONE_DAY("1D"),
    ONE_WEEK("1W"),
    TWO_WEEEKS("2W"),
    ONE_MONTH("1M"),
    SIX_MONTHS("6M"),
    ONE_YEAR("1Y");

    @Serializer(forClass = ExpireDate::class)
    companion object Ser : KSerializer<ExpireDate> {
        override val descriptor: SerialDescriptor = PrimitiveDescriptor("$MODEL_PACKAGE.ExpireDate", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): ExpireDate {
            val value = decoder.decodeString()
            return values().firstOrNull { it.code == value } ?: throw NoSuchElementException("Can't find ExpireDate with key $value")
        }

        override fun serialize(encoder: Encoder, value: ExpireDate) {
            encoder.encodeString(value.code)
        }
    }
}