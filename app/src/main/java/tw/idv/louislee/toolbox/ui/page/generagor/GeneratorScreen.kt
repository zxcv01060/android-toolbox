package tw.idv.louislee.toolbox.ui.page.generagor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.util.UUID

@Composable
fun GeneratorScreen(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: GeneratorViewModel = hiltViewModel()
) {
    Content(drawerState = drawerState, generateUuid = viewModel::generateUuid)
}

@Composable
private fun Content(drawerState: DrawerState, generateUuid: () -> String) {
    ToolboxTheme {
        AppToolbar(
            title = stringResource(id = R.string.generator_title),
            drawerState = drawerState
        ) {
            Column(
                modifier = Modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                UuidGenerator(generateUuid = generateUuid)
            }
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    Content(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        generateUuid = { UUID.randomUUID().toString() }
    )
}
