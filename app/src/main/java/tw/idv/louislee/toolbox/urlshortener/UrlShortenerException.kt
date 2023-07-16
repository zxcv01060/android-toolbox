package tw.idv.louislee.toolbox.urlshortener

import tw.idv.louislee.toolbox.exception.AppException

open class UrlShortenerException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause) {
}