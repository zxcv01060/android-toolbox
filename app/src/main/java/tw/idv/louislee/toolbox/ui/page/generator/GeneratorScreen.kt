package tw.idv.louislee.toolbox.ui.page.generator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringGeneratorState
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.util.UUID

private const val TAB_UUID = 0
private const val TAB_DATABASE_CONNECTION_STRING = 1
private const val TAB_C_SHARP_DB_SCAFFOLD = 2

@Composable
fun GeneratorScreen(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: GeneratorViewModel = hiltViewModel()
) {
    Content(
        drawerState = drawerState,
        generateUuid = viewModel::generateUuid,
        databaseConnectionStringState = viewModel.databaseConnectionStringState,
        generateConnectionString = viewModel::generateConnectionString
    )
}

@Composable
private fun Content(
    drawerState: DrawerState,
    generateUuid: () -> String,
    databaseConnectionStringState: DatabaseConnectionStringGeneratorState,
    generateConnectionString: () -> String
) {
    ToolboxTheme {
        AppToolbar(
            title = stringResource(id = R.string.generator_title), drawerState = drawerState
        ) {
            var selectedTabIndex by remember {
                mutableStateOf(TAB_UUID)
            }

            Column {
                GeneratorTabRow(selectedTabIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it })

                when (selectedTabIndex) {
                    TAB_UUID -> UuidGenerator(generateUuid = generateUuid)
                    TAB_DATABASE_CONNECTION_STRING -> DatabaseConnectionStringGenerator(
                        modifier = Modifier.padding(all = 8.dp),
                        state = databaseConnectionStringState,
                        generate = generateConnectionString
                    )

                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun GeneratorTabRow(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        Tab(selected = selectedTabIndex == TAB_UUID,
            text = { Text(text = stringResource(id = R.string.generator_uuid_title)) },
            onClick = { onTabSelected(TAB_UUID) })
        Tab(selected = selectedTabIndex == TAB_DATABASE_CONNECTION_STRING,
            text = { Text(text = stringResource(id = R.string.generator_database_connection_string_title)) },
            onClick = { onTabSelected(TAB_DATABASE_CONNECTION_STRING) })
        Tab(selected = selectedTabIndex == TAB_C_SHARP_DB_SCAFFOLD,
            text = { Text(text = stringResource(id = R.string.generator_c_sharp_db_scaffold_title)) },
            onClick = { onTabSelected(TAB_C_SHARP_DB_SCAFFOLD) })
    }
}

@AppPreview
@Composable
private fun Preview() {
    val databaseConnectionStringState = DatabaseConnectionStringGeneratorState()

    Content(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        generateUuid = { UUID.randomUUID().toString() },
        databaseConnectionStringState = databaseConnectionStringState,
        generateConnectionString = { "" })
}
