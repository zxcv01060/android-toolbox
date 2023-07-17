package tw.idv.louislee.toolbox.encryption.algorithm

interface EncryptionAlgorithm {
    fun encode(plainText: String): String
}

interface DecryptionAlgorithm {
    fun decode(encodedText: String): String
}
