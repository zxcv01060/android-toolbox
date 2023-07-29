package tw.idv.louislee.toolbox.encryption.algorithm

import android.util.Base64
import tw.idv.louislee.toolbox.encryption.EncodedResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Base64EncryptionAlgorithm @Inject constructor() : EncryptionAlgorithm, DecryptionAlgorithm {
    override fun encode(plainText: String): EncodedResult = EncodedResult.TextEncodedResult(
        Base64.encodeToString(
            plainText.toByteArray(),
            Base64.NO_WRAP
        )
    )

    override fun decode(encodedText: String): String =
        String(Base64.decode(encodedText, Base64.DEFAULT))
}