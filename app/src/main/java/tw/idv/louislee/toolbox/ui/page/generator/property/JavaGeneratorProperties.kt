package tw.idv.louislee.toolbox.ui.page.generator.property

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringGeneratorState
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@Composable
fun JavaGeneratorProperties(state: DatabaseConnectionStringGeneratorState) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        val state = DatabaseConnectionStringGeneratorState()

        JavaGeneratorProperties(state = state)
    }
}
