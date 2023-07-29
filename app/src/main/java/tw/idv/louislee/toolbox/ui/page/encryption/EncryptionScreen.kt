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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.json.JSONObject
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
        onEncodedTextChange = { viewModel.encodedText = it },
        jwtHeader = viewModel.jwtHeader,
        jwtPayload = viewModel.jwtPayload,
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
    onEncodedTextChange: (String) -> Unit,
    jwtHeader: String,
    jwtPayload: String,
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
            Column(
                modifier = Modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.encryption_plain_text)) },
                    value = plainText,
                    onValueChange = onPlainTextChange
                )

                AlgorithmOptions(
                    algorithmType = algorithmType,
                    onAlgorithmTypeChange = onAlgorithmTypeChange,
                    encode = encode,
                    decode = decode
                )

                DecodeForm(
                    algorithmType = algorithmType,
                    encodedText = encodedText,
                    onEncodedTextChange = onEncodedTextChange,
                    jwtHeader = jwtHeader,
                    jwtPayload = jwtPayload,
                )
            }
        }
    }
}

@Composable
private fun AlgorithmOptions(
    algorithmType: EncryptionAlgorithmType,
    onAlgorithmTypeChange: (EncryptionAlgorithmType) -> Unit,
    encode: () -> Unit,
    decode: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EncryptionAlgorithmTypeDropdown(
            modifier = Modifier.weight(1f),
            selectedAlgorithmType = algorithmType,
            onAlgorithmTypeChange = onAlgorithmTypeChange
        )

        Button(enabled = algorithmType.isCanBeEncode, onClick = encode) {
            Text(text = stringResource(id = R.string.encryption_encode))
        }

        Button(enabled = algorithmType.isCanBeDecode, onClick = decode) {
            Text(text = stringResource(id = R.string.encryption_decode))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EncryptionAlgorithmTypeDropdown(
    modifier: Modifier,
    selectedAlgorithmType: EncryptionAlgorithmType,
    onAlgorithmTypeChange: (EncryptionAlgorithmType) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }) {
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
            for (algorithmType in EncryptionAlgorithmType.entries) {
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

@Composable
private fun DecodeForm(
    algorithmType: EncryptionAlgorithmType,
    encodedText: String,
    onEncodedTextChange: (String) -> Unit,
    jwtHeader: String,
    jwtPayload: String
) {
    when (algorithmType) {
        EncryptionAlgorithmType.JWT -> JwtEncodeForm(
            encodedText = encodedText,
            onEncodedTextChange = onEncodedTextChange,
            header = jwtHeader,
            payload = jwtPayload
        )

        else -> TextEncodeForm(
            encodedText = encodedText,
            onEncodedTextChange = onEncodedTextChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JwtEncodeForm(
    encodedText: String,
    onEncodedTextChange: (String) -> Unit,
    header: String,
    payload: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.encryption_encoded_text)) },
            value = encodedText,
            onValueChange = onEncodedTextChange
        )

        if (header.isNotBlank()) {
            Column {
                Text(text = stringResource(id = R.string.encryption_jwt_header))

                Text(text = JSONObject(header).toString(4))
            }
        }

        if (payload.isNotBlank()) {
            Column {
                Text(text = stringResource(id = R.string.encryption_jwt_payload))

                Text(text = JSONObject(payload).toString(4))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextEncodeForm(
    encodedText: String,
    onEncodedTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.encryption_encoded_text)) },
        trailingIcon = {
            if (encodedText.isBlank()) {
                return@OutlinedTextField
            }

            AppCopyButton(copyText = encodedText)
        },
        value = encodedText,
        onValueChange = onEncodedTextChange
    )
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
    var jwtHeader by remember {
        mutableStateOf("")
    }
    var jwtPayload by remember {
        mutableStateOf("")
    }

    Content(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        algorithmType = algorithmType,
        plainText = plainText,
        encodedText = encodedText,
        onEncodedTextChange = { encodedText = it },
        jwtHeader = jwtHeader,
        jwtPayload = jwtPayload,
        onAlgorithmTypeChange = { algorithmType = it },
        onPlainTextChange = { plainText = it.trim() },
        encode = {}
    ) {}
}
