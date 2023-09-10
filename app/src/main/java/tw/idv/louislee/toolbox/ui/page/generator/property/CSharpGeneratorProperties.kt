package tw.idv.louislee.toolbox.ui.page.generator.property

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringGeneratorState
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringProperty
import tw.idv.louislee.toolbox.generator.DatabaseType
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.form.AppCheckbox
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@Composable
fun CSharpGeneratorProperties(state: DatabaseConnectionStringGeneratorState) {
    when (state.databaseType) {
        DatabaseType.MY_SQL -> CSharpMySqlGenerator(state = state)
        DatabaseType.SQL_SERVER -> CSharpSqlServerGenerator(state = state)
        null -> Unit
    }
}

@Composable
private fun CSharpMySqlGenerator(state: DatabaseConnectionStringGeneratorState) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        var commandTimeoutSecond by remember {
            mutableStateOf<UInt?>(null)
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(text = stringResource(id = R.string.generator_database_connection_string_property_my_sql_default_command_timeout))
            },
            value = commandTimeoutSecond?.toString() ?: "",
            onValueChange = {
                commandTimeoutSecond = it.toUIntOrNull() ?: commandTimeoutSecond
                state[DatabaseConnectionStringProperty.CSharp.MySql.CONNECTION_TIMEOUT] =
                    commandTimeoutSecond?.toString()
            },
            supportingText = {
                Text(
                    text = stringResource(
                        id = R.string.generator_database_connection_string_property_my_sql_default_command_timeout_hint,
                        30
                    )
                )
            }
        )

        var isTreatTinyIntAsStringChecked by remember {
            mutableStateOf(false)
        }
        AppCheckbox(
            label = stringResource(id = R.string.generator_database_connection_string_property_my_sql_treat_tiny_int_as_boolean),
            isChecked = isTreatTinyIntAsStringChecked,
            onCheckedChange = {
                isTreatTinyIntAsStringChecked = it
                state[DatabaseConnectionStringProperty.CSharp.MySql.TINY_INT_AS_BOOLEAN] = if (it) {
                    "true"
                } else {
                    null
                }
            }
        )
    }
}

@Composable
private fun CSharpSqlServerGenerator(state: DatabaseConnectionStringGeneratorState) {

}

@AppPreview
@Composable
private fun MySqlPreview() {
    ToolboxTheme {
        val state = DatabaseConnectionStringGeneratorState()
        state.databaseType = DatabaseType.MY_SQL

        CSharpGeneratorProperties(state = state)
    }
}

@AppPreview
@Composable
private fun SqlServerPreview() {
    ToolboxTheme {
        val state = DatabaseConnectionStringGeneratorState()
        state.databaseType = DatabaseType.SQL_SERVER

        CSharpGeneratorProperties(state = state)
    }
}
