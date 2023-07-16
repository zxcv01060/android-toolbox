package tw.idv.louislee.toolbox.service

import tw.idv.louislee.toolbox.UrlShortenerDto
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerResolverChainFactory
import javax.inject.Inject

interface UrlShortenerService {
    suspend fun getOriginalUrl(shortenerUrl: String): String
}

internal class UrlShortenerServiceImpl @Inject constructor(
    private val chainFactory: UrlShortenerResolverChainFactory,
    private val jsoupService: JsoupService
) : UrlShortenerService {
    override suspend fun getOriginalUrl(shortenerUrl: String): String {
        val chain = chainFactory.create()

        return chain.resolve(
            UrlShortenerDto(
                url = shortenerUrl,
                html = jsoupService.getHtml(shortenerUrl)
            )
        )
    }
}
