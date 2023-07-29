package tw.idv.louislee.toolbox.encryption.algorithm

import tw.idv.louislee.toolbox.encryption.EncodedResult
import java.security.MessageDigest

class MessageDigestEncryptionAlgorithm(algorithm: String) : EncryptionAlgorithm {
    private val messageDigest = MessageDigest.getInstance(algorithm)

    override fun encode(plainText: String): EncodedResult {
        return EncodedResult.TextEncodedResult(
            messageDigest.digest(plainText.toByteArray())
                .joinToString("") { "%02x".format(it) }
        )
    }
}