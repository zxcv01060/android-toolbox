package tw.idv.louislee.toolbox.encryption.algorithm

import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType.BASE_64
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType.JWT
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType.MD5
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType.SHA_1
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType.SHA_256
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType.SHA_384
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType.SHA_512
import javax.inject.Inject

fun interface EncryptionAlgorithmProvider {
    fun provide(type: EncryptionAlgorithmType): EncryptionAlgorithm
}

class EncryptionAlgorithmProviderImpl @Inject constructor(
    private val base64Algorithm: Base64EncryptionAlgorithm,
    private val md5Algorithm: EncryptionAlgorithm,
    private val sha1Algorithm: EncryptionAlgorithm,
    private val sha256Algorithm: EncryptionAlgorithm,
    private val sha384Algorithm: EncryptionAlgorithm,
    private val sha512Algorithm: EncryptionAlgorithm
) : EncryptionAlgorithmProvider {
    override fun provide(type: EncryptionAlgorithmType): EncryptionAlgorithm = when (type) {
        BASE_64 -> base64Algorithm
        MD5 -> md5Algorithm
        SHA_1 -> sha1Algorithm
        SHA_256 -> sha256Algorithm
        SHA_384 -> sha384Algorithm
        SHA_512 -> sha512Algorithm
        JWT -> TODO()
    }
}
