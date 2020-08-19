package net.pearx.kpastebin

import net.pearx.kpastebin.model.Privacy

/** Thrown by [PastebinClient] when provided user key is invalid, expired or null. */
public class InvalidUserKeyException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

/** Thrown by [PastebinClient] when paste limit with specified [privacy] for current user is exceeded. */
public class FreePasteLimitException(public val privacy: Privacy, message: String, cause: Throwable? = null) : PastebinException(message, cause)

/** Thrown by [PastebinClient] when paste text exceeds the size limit. */
public class PasteSizeException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

/** Thrown by [PastebinClient] when trying to create a paste with empty text. */
public class EmptyPasteException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

/**
 * Thrown by [PastebinClient] when specified paste syntax highlighting format doesn't exist.
 * Please see [Pastebin documentation](https://pastebin.com/doc_api#5) for the list of available paste formats.
 */
public class InvalidPasteFormatException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

/** Thrown by [PastebinClient] when provided login/password combination is invalid. */
public class InvalidLoginException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

/** Thrown by [PastebinClient] when used account isn't active. */
public class AccountNotActiveException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

/** Thrown by [PastebinClient] when paste with specified key doesn't exist or you don't have permissions to view or delete it. */
public class PasteNotFoundException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

/** Thrown by [PastebinClient] when paste publication limit for current IP per day is exceeded. */
public class PastePerDayLimitException(message: String, cause: Throwable? = null) : PastebinException(message, cause)

/** Generic Pastebin exception thrown by [PastebinClient]. */
public open class PastebinException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)