package tw.idv.louislee.toolbox.urlshortener

import tw.idv.louislee.toolbox.UrlShortenerDto

class UrlShortenerNotSupportedException(
    val shortener: UrlShortenerDto,
    message: String? = null
) : UrlShortenerException(message)
