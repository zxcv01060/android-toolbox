package tw.idv.louislee.toolbox.generator

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class GeneratorModule {
    @Binds
    abstract fun databaseConnectionStringBuilderFactory(
        impl: DatabaseConnectionStringBuilderFactoryImpl
    ): DatabaseConnectionStringBuilderFactory
}