package tw.idv.louislee.toolbox.encryption

import androidx.annotation.StringRes
import tw.idv.louislee.toolbox.R

enum class EncryptionAlgorithmType(
    @StringRes val title: Int,
    val isCanBeEncode: Boolean,
    val isCanBeDecode: Boolean
) {
    BASE_64(R.string.encryption_base_64, true, true),
    MD5(R.string.encryption_md5, true, false),
    SHA_1(R.string.encryption_sha_1, true, false),
    SHA_256(R.string.encryption_sha_256, true, false),
    SHA_384(R.string.encryption_sha_384, true, false),
    SHA_512(R.string.encryption_sha_512, true, false),
    JWT(R.string.encryption_jwt, true, true)
}