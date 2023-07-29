package tw.idv.louislee.toolbox.ui.page.encryption

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tw.idv.louislee.toolbox.encryption.EncodedResult
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType
import tw.idv.louislee.toolbox.service.EncryptionService
import javax.inject.Inject

@HiltViewModel
class EncryptionViewModel @Inject constructor(
    private val encryptionService: EncryptionService
) : ViewModel() {
    var algorithmType by mutableStateOf(EncryptionAlgorithmType.BASE_64)
    var plainText by mutableStateOf("")
    var encodedText by mutableStateOf("")
    var jwtHeader by mutableStateOf("")
    var jwtPayload by mutableStateOf("")

    fun encode() {
        when (val encodedResult = encryptionService.encode(algorithmType, plainText.trim())) {
            EncodedResult.Blank -> encodedText = ""
            is EncodedResult.JwtEncodedResult -> {
                jwtHeader = encodedResult.header
                jwtPayload = encodedResult.payload
            }

            is EncodedResult.TextEncodedResult -> encodedText = encodedResult.encodedText
        }
    }

    fun decode() {
        if (algorithmType == EncryptionAlgorithmType.JWT) {
            decodeJwtToken()
            return
        }

        plainText = encryptionService.decode(algorithmType, encodedText.trim())
    }

    private fun decodeJwtToken() {
        val splitJwtToken = encodedText.split('.')
        jwtHeader = encryptionService.decode(EncryptionAlgorithmType.BASE_64, splitJwtToken[0])
        jwtPayload = encryptionService.decode(EncryptionAlgorithmType.BASE_64, splitJwtToken[1])
    }
}