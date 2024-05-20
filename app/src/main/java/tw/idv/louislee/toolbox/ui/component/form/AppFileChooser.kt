package tw.idv.louislee.toolbox.ui.component.form

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import tw.idv.louislee.toolbox.ui.utils.getFilename

@Composable
fun AppFileChooser(
    mimeType: String,
    placeholder: String,
    filename: String?,
    onFileChoose: (Uri) -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String = stringResource(id = R.string.common_choose_file),
    isCanBeClear: Boolean = true,
    requestClearSelectedFile: () -> Unit = {}
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        it?.let(onFileChoose)
    }

    Row(
        modifier = Modifier.fillMaxWidth() then modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            readOnly = true,
            placeholder = {
                Text(text = placeholder)
            },
            trailingIcon = {
                if (!isCanBeClear) {
                    return@OutlinedTextField
                }

                if (filename == null) {
                    return@OutlinedTextField
                }

                IconButton(onClick = { requestClearSelectedFile() }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(id = R.string.common_clear)
                    )
                }
            },
            value = filename ?: "",
            onValueChange = {}
        )

        Button(onClick = { launcher.launch(mimeType) }) {
            Text(text = buttonText)
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        val context = LocalContext.current

        var filename by remember {
            mutableStateOf<String?>(null)
        }

        AppFileChooser(
            mimeType = "application/pdf",
            placeholder = "文件",
            filename = filename,
            requestClearSelectedFile = {
                filename = ""
            },
            onFileChoose = {
                println(it)
                filename = it.getFilename(context)
            }
        )
    }
}
