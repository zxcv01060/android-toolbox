package tw.idv.louislee.toolbox.encryption

import androidx.annotation.StringRes
import tw.idv.louislee.toolbox.R

enum class EncryptionAlgorithmType(@StringRes val title: Int) {
    BASE_64(R.string.encryption_base_64),
    MD5(R.string.encryption_md5),
    SHA_1(R.string.encryption_sha_1),
    SHA_256(R.string.encryption_sha_256),
    SHA_384(R.string.encryption_sha_384),
    SHA_512(R.string.encryption_sha_512),
    JWT(R.string.encryption_jwt)
}