package tw.idv.louislee.toolbox.ui.page.encryption

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.core.text.trimmedLength
import androidx.hilt.navigation.compose.hiltViewModel
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.encryption.EncryptionAlgorithmType
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.component.button.AppCopyButton
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncryptionScreen(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: EncryptionViewModel = hiltViewModel()
) {
    Content(
        drawerState = drawerState,
        algorithmType = viewModel.algorithmType,
        plainText = viewModel.plainText,
        encodedText = viewModel.encodedText,
        onAlgorithmTypeChange = { viewModel.algorithmType = it },
        onPlainTextChange = { viewModel.plainText = it.trim() },
        encode = viewModel::encode,
        decode = viewModel::decode
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    drawerState: DrawerState,
    algorithmType: EncryptionAlgorithmType,
    plainText: String,
    encodedText: String,
    onAlgorithmTypeChange: (EncryptionAlgorithmType) -> Unit,
    onPlainTextChange: (String) -> Unit,
    encode: () -> Unit,
    decode: () -> Unit
) {
    ToolboxTheme {
        AppToolbar(
            title = stringResource(id = R.string.encryption_title),
            drawerState = drawerState
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                EncryptionForm(
                    algorithmType = algorithmType,
                    plainText = plainText,
                    encodedText = encodedText,
                    onAlgorithmTypeChange = onAlgorithmTypeChange,
                    onPlainTextChange = onPlainTextChange,
                    encode = encode
                )

                if (encodedText.isBlank()) {
                    return@Column
                }

                Text(
                    text = stringResource(
                        id = R.string.encryption_encode_success,
                        encodedText.trimmedLength()
                    )
                )

                Text(text = encodedText)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EncryptionForm(
    algorithmType: EncryptionAlgorithmType,
    plainText: String,
    encodedText: String,
    onAlgorithmTypeChange: (EncryptionAlgorithmType) -> Unit,
    onPlainTextChange: (String) -> Unit,
    encode: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EncryptionAlgorithmTypeDropdown(
            selectedAlgorithmType = algorithmType,
            onAlgorithmTypeChange = onAlgorithmTypeChange
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.encryption_plain_text)) },
            value = plainText,
            onValueChange = onPlainTextChange
        )

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Button(onClick = encode) {
                Text(text = stringResource(id = R.string.common_submit))
            }

            AppCopyButton(
                buttonText = R.string.encryption_copy_encoded_text,
                copyText = encodedText
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EncryptionAlgorithmTypeDropdown(
    selectedAlgorithmType: EncryptionAlgorithmType,
    onAlgorithmTypeChange: (EncryptionAlgorithmType) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text(text = stringResource(id = R.string.encryption_algorithm_type)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            value = stringResource(id = selectedAlgorithmType.title),
            onValueChange = {}
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            for (algorithmType in EncryptionAlgorithmType.values()) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = algorithmType.title)) },
                    onClick = {
                        onAlgorithmTypeChange(algorithmType)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@AppPreview
@Composable
private fun Preview() {
    var algorithmType by remember {
        mutableStateOf(EncryptionAlgorithmType.BASE_64)
    }
    var plainText by remember {
        mutableStateOf("")
    }
    var encodedText by remember {
        mutableStateOf("")
    }

    Content(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        algorithmType = algorithmType,
        plainText = plainText,
        encodedText = encodedText,
        onAlgorithmTypeChange = { algorithmType = it },
        onPlainTextChange = { plainText = it },
        encode = {},
        decode = {}
    )
}
