package net.pearx.kpastebin.model

import kotlinx.serialization.*
import net.pearx.kpastebin.internal.EnumIntSerializer
import net.pearx.kpastebin.internal.MODEL_PACKAGE

@Serializable(with = Privacy.Ser::class)
public enum class Privacy {
    PUBLIC,
    UNLISTED,
    PRIVATE;

    internal companion object Ser : EnumIntSerializer<Privacy>("$MODEL_PACKAGE.Privacy", values())
}