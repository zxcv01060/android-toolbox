package tw.idv.louislee.toolbox.ui.page.generator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringGeneratorState
import tw.idv.louislee.toolbox.generator.DatabaseConnectionStringSupportType
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.button.AppCopyButton
import tw.idv.louislee.toolbox.ui.component.form.AppCheckbox
import tw.idv.louislee.toolbox.ui.component.form.AppDropdown
import tw.idv.louislee.toolbox.ui.page.generator.property.CSharpGeneratorProperties
import tw.idv.louislee.toolbox.ui.page.generator.property.DbMateGeneratorProperties
import tw.idv.louislee.toolbox.ui.page.generator.property.JavaGeneratorProperties
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@Composable
fun DatabaseConnectionStringGenerator(
    state: DatabaseConnectionStringGeneratorState,
    generate: () -> String,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState) then modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppDropdown(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.generator_database_connection_string_type),
            optionLabelSupplier = {
                stringResource(id = it.titleId)
            },
            onOptionSelected = { state.supportType = it },
            selectedOptionValue = state.supportType
        )

        AppDropdown(
            label = stringResource(id = R.string.generator_database_connection_string_database_type),
            optionLabelSupplier = { stringResource(id = it.titleId) },
            onOptionSelected = {
                if (state.port == 0u || state.port == state.databaseType?.defaultPort) {
                    state.port = it.defaultPort
                }

                state.databaseType = it
            },
            selectedOptionValue = state.databaseType
        )

        DatabaseHost(
            host = state.host,
            onHostChange = { state.host = it },
            port = state.port,
            onPortChange = { state.port = it }
        )

        DatabaseUser(state = state)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.generator_database_connection_string_database_type))
            },
            value = state.database,
            onValueChange = { state.database = it }
        )

        when (state.supportType) {
            DatabaseConnectionStringSupportType.JAVA -> JavaGeneratorProperties(state = state)

            DatabaseConnectionStringSupportType.CSHARP -> CSharpGeneratorProperties(state = state)

            DatabaseConnectionStringSupportType.DB_MATE -> DbMateGeneratorProperties(state = state)
        }

        var result by remember {
            mutableStateOf("")
        }
        Button(onClick = { result = generate() }) {
            Text(text = stringResource(id = R.string.common_submit))
        }

        if (result.isBlank()) {
            return
        }
        Row {
            Text(text = "結果：${result.replace(state.password, "***")}")

            AppCopyButton(copyText = result)
        }
    }
}

@Composable
private fun DatabaseHost(
    host: String,
    onHostChange: (String) -> Unit,
    port: UInt,
    onPortChange: (UInt) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            label = {
                Text(text = stringResource(id = R.string.generator_database_connection_string_host))
            },
            value = host,
            onValueChange = onHostChange
        )

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(text = stringResource(id = R.string.generator_database_connection_string_port))
            },
            value = port.toString(),
            onValueChange = { onPortChange(it.toUIntOrNull() ?: port) }
        )
    }
}

@Composable
private fun DatabaseUser(state: DatabaseConnectionStringGeneratorState) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = {
                Text(text = stringResource(id = R.string.generator_database_connection_string_account))
            },
            value = state.account,
            onValueChange = { state.account = it }
        )

        Column(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text(text = stringResource(id = R.string.generator_database_connection_string_password))
                },
                value = state.password,
                onValueChange = { state.password = it }
            )

            AppCheckbox(
                label = stringResource(id = R.string.generator_database_connection_string_is_password_encodedBy_base64),
                isChecked = state.isPasswordEncodedByBase64,
                onCheckedChange = { state.isPasswordEncodedByBase64 = it }
            )
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        val state = DatabaseConnectionStringGeneratorState()

        DatabaseConnectionStringGenerator(
            state = state,
            generate = { "新產生的連線字串" },
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        )
    }
}
