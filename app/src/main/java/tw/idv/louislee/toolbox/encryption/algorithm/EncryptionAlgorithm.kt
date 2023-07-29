package tw.idv.louislee.toolbox.encryption.algorithm

import tw.idv.louislee.toolbox.encryption.EncodedResult

interface EncryptionAlgorithm {
    fun encode(plainText: String): EncodedResult
}

interface DecryptionAlgorithm {
    fun decode(encodedText: String): String
}
