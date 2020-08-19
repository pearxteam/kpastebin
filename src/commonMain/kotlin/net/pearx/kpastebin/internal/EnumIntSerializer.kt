package net.pearx.kpastebin.internal

import kotlinx.serialization.*

internal open class EnumIntSerializer<T>(private val serialName: String, private val values: Array<T>) : KSerializer<T> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor(serialName, PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): T {
        val value = decoder.decodeInt()
        return values[value] ?: throw NoSuchElementException("Can't find $serialName with key $value")
    }

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeInt(values.indexOf(value))
    }
}