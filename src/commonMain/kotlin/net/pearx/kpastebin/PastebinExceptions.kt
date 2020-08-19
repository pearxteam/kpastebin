package net.pearx.kpastebin

import net.pearx.kpastebin.model.Privacy

public class InvalidUserKeyException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

public class FreePasteLimitException(public val privacy: Privacy, message: String, cause: Throwable? = null) : PastebinException(message, cause)
public class PasteSizeException(message: String, cause: Throwable? = null) : PastebinException(message, cause)
public class EmptyPasteException(message: String, cause: Throwable? = null) : PastebinException(message, cause)
public class InvalidPasteFormatException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

public class InvalidLoginException(message: String, cause: Throwable? = null) : PastebinException(message, cause)
public class AccountNotActiveException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

public class InvalidPermissionException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

public class PasteNotFoundException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

public class PastePerDayLimitException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

public open class PastebinException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)