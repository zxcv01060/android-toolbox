package tw.idv.louislee.toolbox.ui.page.generagor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.component.button.AppCopyButton
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

@Composable
private fun UuidGenerator(generateUuid: () -> String) {
    var uuid by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "UUID", style = MaterialTheme.typography.headlineSmall)

            GenerateButton(onClick = { uuid = generateUuid() })
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = uuid)

            if (uuid.isNotBlank()) {
                AppCopyButton(copyText = uuid)
            }
        }
    }
}

@Composable
private fun GenerateButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = stringResource(id = R.string.generator_generate))
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
