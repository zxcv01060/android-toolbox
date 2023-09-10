package tw.idv.louislee.toolbox.ui.page.generator

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringGeneratorState
import tw.idv.louislee.toolbox.service.GeneratorService
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GeneratorViewModel @Inject constructor(private val service: GeneratorService) : ViewModel() {
    val databaseConnectionStringState = DatabaseConnectionStringGeneratorState()

    fun generateUuid(): String = UUID.randomUUID().toString()

    fun generateConnectionString(): String =
        service.generateConnectionString(databaseConnectionStringState)
}