package tw.idv.louislee.toolbox.ui.page.pdf.tab.separate

import android.os.Environment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import tw.idv.louislee.toolbox.model.UriFile
import tw.idv.louislee.toolbox.service.PdfService
import javax.inject.Inject

@HiltViewModel
class PdfSeparateTabViewModel @Inject constructor(private val service: PdfService) : ViewModel() {
    private val _state = MutableStateFlow(PdfSeparateTabState())
    val state = _state.asStateFlow()

    fun onIsZippedChange(isZipped: Boolean) {
        _state.update { it.copy(isZipped = isZipped) }
    }

    suspend fun separate(file: UriFile) {
        val downloadPath = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS
        )
        val separateEventFlow = service.separate(
            file.filename,
            file.inputStreamSupplier(),
            _state.value.isZipped,
            downloadPath.toPath()
        )
        separateEventFlow.flowOn(Dispatchers.IO)
            .collectLatest { separateEvent ->
                _state.update {
                    it.copy(
                        processedFileCount = separateEvent.processedCount,
                        totalFileCount = separateEvent.totalFileCount,
                        isProcessingFiles = separateEvent.processedCount != separateEvent.totalFileCount
                    )
                }
            }
    }

    fun dismissProgressDialog() {
        _state.update {
            it.copy(isProcessingFiles = false)
        }
    }
}

data class PdfSeparateTabState(
    val totalFileCount: Int = 0,
    val processedFileCount: Int = 0,
    val isProcessingFiles: Boolean = false,
    val isZipped: Boolean = false
)
