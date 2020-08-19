package net.pearx.kpastebin.model

import kotlinx.serialization.Serializable
import net.pearx.kpastebin.internal.EnumIntSerializer
import net.pearx.kpastebin.internal.MODEL_PACKAGE

@Serializable(with = AccountType.Ser::class)
public enum class AccountType
{
    NORMAL,
    PRO;

    internal companion object Ser : EnumIntSerializer<AccountType>("$MODEL_PACKAGE.AccountType", values())
}