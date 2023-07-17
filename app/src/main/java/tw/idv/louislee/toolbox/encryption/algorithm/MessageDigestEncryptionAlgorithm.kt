package tw.idv.louislee.toolbox.encryption.algorithm

import java.security.MessageDigest

class MessageDigestEncryptionAlgorithm(algorithm: String) : EncryptionAlgorithm {
    private val messageDigest = MessageDigest.getInstance(algorithm)

    override fun encode(plainText: String): String {
        return messageDigest.digest(plainText.toByteArray())
            .joinToString("") { "%02x".format(it) }
    }
}