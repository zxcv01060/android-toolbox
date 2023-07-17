package tw.idv.louislee.toolbox.ui.page.encryption

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
        private set

    fun encode() {
        encodedText = encryptionService.encode(algorithmType, plainText.trim())
    }

    fun decode() {
        plainText = encryptionService.decode(algorithmType, encodedText.trim())
    }
}