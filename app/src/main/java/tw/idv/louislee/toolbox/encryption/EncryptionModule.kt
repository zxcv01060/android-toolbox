package tw.idv.louislee.toolbox.encryption

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tw.idv.louislee.toolbox.encryption.algorithm.Base64EncryptionAlgorithm
import tw.idv.louislee.toolbox.encryption.algorithm.EncryptionAlgorithmProvider
import tw.idv.louislee.toolbox.encryption.algorithm.EncryptionAlgorithmProviderImpl
import tw.idv.louislee.toolbox.encryption.algorithm.MessageDigestEncryptionAlgorithm

@Module
@InstallIn(SingletonComponent::class)
class EncryptionModule {
    @Provides
    fun encryptionAlgorithmProvider(base64EncryptionAlgorithm: Base64EncryptionAlgorithm): EncryptionAlgorithmProvider {
        return EncryptionAlgorithmProviderImpl(
            base64Algorithm = base64EncryptionAlgorithm,
            md5Algorithm = MessageDigestEncryptionAlgorithm("MD5"),
            sha1Algorithm = MessageDigestEncryptionAlgorithm("SHA-1"),
            sha256Algorithm = MessageDigestEncryptionAlgorithm("SHA-256"),
            sha384Algorithm = MessageDigestEncryptionAlgorithm("SHA-384"),
            sha512Algorithm = MessageDigestEncryptionAlgorithm("SHA-512")
        )
    }
}