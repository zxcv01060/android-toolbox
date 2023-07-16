package tw.idv.louislee.toolbox.urlshortener

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import tw.idv.louislee.toolbox.UrlShortenerDto
import javax.inject.Inject

fun interface UrlShortenerResolver {
    suspend fun resolve(shortener: UrlShortenerDto, chain: UrlShortenerResolverChain): String
}

internal class ReurlShortenerResolver @Inject constructor() : UrlShortenerResolver {
    private companion object {
        private const val URL_PREFIX = "https://reurl.cc"
    }

    override suspend fun resolve(
        shortener: UrlShortenerDto,
        chain: UrlShortenerResolverChain
    ): String = if (shortener.url.startsWith(URL_PREFIX)) {
        resolve(shortener)
    } else {
        chain.resolve(shortener)
    }

    private suspend fun resolve(shortener: UrlShortenerDto): String = withContext(Dispatchers.IO) {
        val body = Jsoup.parse(shortener.html)
            .body()
        val child = body.child(0)
        if (child.tagName() != "input") {
            throw UrlShortenerException("此網址不存在，請檢查網址是否正確")
        }

        child.attr("value")
    }
}
