package tw.idv.louislee.toolbox.service

import tw.idv.louislee.toolbox.encryption.EncodedResult
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType
import tw.idv.louislee.toolbox.encryption.algorithm.DecryptionAlgorithm
import tw.idv.louislee.toolbox.encryption.algorithm.EncryptionAlgorithmProvider
import javax.inject.Inject

interface EncryptionService {
    fun encode(algorithmType: EncryptionAlgorithmType, plainText: String): EncodedResult

    fun decode(algorithmType: EncryptionAlgorithmType, encodedText: String): String
}

class EncryptionServiceImpl @Inject constructor(
    private val provider: EncryptionAlgorithmProvider
) : EncryptionService {
    override fun encode(algorithmType: EncryptionAlgorithmType, plainText: String): EncodedResult {
        val algorithm = provider.provide(algorithmType)

        return algorithm.encode(plainText)
    }

    override fun decode(algorithmType: EncryptionAlgorithmType, encodedText: String): String {
        val algorithm = provider.provide(algorithmType)
        if (algorithm !is DecryptionAlgorithm) {
            throw IllegalStateException("此演算法無法將密文還原成明文")
        }

        return algorithm.decode(encodedText)
    }
}
