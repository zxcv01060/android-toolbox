package tw.idv.louislee.toolbox.urlshortener

import tw.idv.louislee.toolbox.UrlShortenerDto
import javax.inject.Inject

fun interface UrlShortenerResolverChainFactory {
    fun create(): UrlShortenerResolverChain
}

internal class UrlShortenerResolverChainFactoryImpl @Inject constructor(
    private val resolvers: Set<@JvmSuppressWildcards UrlShortenerResolver>
) : UrlShortenerResolverChainFactory {
    override fun create(): UrlShortenerResolverChain =
        UrlShortenerResolverChainImpl(resolvers.toList())
}

fun interface UrlShortenerResolverChain {
    suspend fun resolve(shortener: UrlShortenerDto): String
}

private class UrlShortenerResolverChainImpl(
    private val resolvers: List<UrlShortenerResolver>
) : UrlShortenerResolverChain {
    private var resolverIndex = 0

    override suspend fun resolve(shortener: UrlShortenerDto): String {
        val resolver = resolvers.getOrNull(resolverIndex++)
            ?: throw UrlShortenerNotSupportedException(
                shortener,
                "目前不支援此短網址：${shortener.url}"
            )

        return resolver.resolve(shortener, this)
    }
}
