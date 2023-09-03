package tw.idv.louislee.toolbox.ui.page.generator

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GeneratorViewModel @Inject constructor() : ViewModel() {
    fun generateUuid(): String = UUID.randomUUID().toString()
}