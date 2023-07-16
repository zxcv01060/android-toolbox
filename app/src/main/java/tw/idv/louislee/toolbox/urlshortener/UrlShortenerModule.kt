package tw.idv.louislee.toolbox.urlshortener

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UrlShortenerModule {
    @Binds
    abstract fun urlShortenerResolverChainFactory(
        impl: UrlShortenerResolverChainFactoryImpl
    ): UrlShortenerResolverChainFactory

    @Binds
    @IntoSet
    abstract fun reurlShortenerResolver(impl: ReurlShortenerResolver): UrlShortenerResolver
}