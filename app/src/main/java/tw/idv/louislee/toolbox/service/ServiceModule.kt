package tw.idv.louislee.toolbox.service

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ServiceModule {
    @Binds
    abstract fun urlShortenerService(impl: UrlShortenerServiceImpl): UrlShortenerService

    @Binds
    abstract fun jsoupService(impl: JsoupServiceImpl): JsoupService

    @Binds
    abstract fun encryptionService(impl: EncryptionServiceImpl): EncryptionService

    @Binds
    abstract fun generatorService(impl: GeneratorServiceImpl): GeneratorService
}