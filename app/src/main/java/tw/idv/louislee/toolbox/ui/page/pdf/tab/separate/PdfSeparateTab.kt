package tw.idv.louislee.toolbox.ui.page.pdf.tab.separate

import android.app.DownloadManager
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.model.UriFile
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.dialog.ProgressDialog
import tw.idv.louislee.toolbox.ui.component.dialog.ProgressDialogState
import tw.idv.louislee.toolbox.ui.component.form.AppCheckbox
import tw.idv.louislee.toolbox.ui.component.form.AppFileChooser
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import tw.idv.louislee.toolbox.ui.utils.parseUri

@Composable
fun PdfSeparateTab(viewModel: PdfSeparateTabViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var file by remember {
        mutableStateOf<UriFile?>(null)
    }
    val coroutineScope = rememberCoroutineScope()

    Content(
        selectedFile = file,
        onFileChoose = { file = it },
        requestClearSelectedFile = { file = null },
        separateFile = {
            val currentFile = file ?: return@Content

            coroutineScope.launch {
                viewModel.separate(currentFile)
            }
        },
        isProcessingFiles = state.isProcessingFiles,
        processedFileCount = state.processedFileCount,
        totalFileCount = state.totalFileCount,
        onDismissProgressDialogRequest = viewModel::dismissProgressDialog,
        isZipped = state.isZipped,
        onIsZippedChange = viewModel::onIsZippedChange
    )
}

@Composable
private fun Content(
    selectedFile: UriFile?,
    onFileChoose: (UriFile) -> Unit,
    requestClearSelectedFile: () -> Unit,
    separateFile: () -> Unit,
    isProcessingFiles: Boolean,
    processedFileCount: Int,
    totalFileCount: Int,
    onDismissProgressDialogRequest: () -> Unit,
    isZipped: Boolean,
    onIsZippedChange: (Boolean) -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SeparateForm(
                selectedFile = selectedFile,
                onFileChoose = onFileChoose,
                requestClearSelectedFile = requestClearSelectedFile,
                isZipped = isZipped,
                onIsZippedChange = onIsZippedChange,
                separateFile = separateFile
            )
        }

        SeparateProgressDialog(
            isProcessingFiles = isProcessingFiles,
            processedFileCount = processedFileCount,
            totalFileCount = totalFileCount,
            onDismissRequest = onDismissProgressDialogRequest,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
private fun SeparateForm(
    selectedFile: UriFile?,
    onFileChoose: (UriFile) -> Unit,
    requestClearSelectedFile: () -> Unit,
    isZipped: Boolean,
    onIsZippedChange: (Boolean) -> Unit,
    separateFile: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val context = LocalContext.current

        AppFileChooser(
            mimeType = "application/pdf",
            placeholder = stringResource(id = R.string.pdf_choose_file),
            filename = selectedFile?.filename,
            onFileChoose = {
                onFileChoose(it.parseUri(context))
            },
            requestClearSelectedFile = requestClearSelectedFile
        )

        AppCheckbox(
            label = stringResource(id = R.string.pdf_separate_is_zipped),
            isChecked = isZipped,
            onCheckedChange = onIsZippedChange
        )

        Button(onClick = separateFile) {
            Text(text = stringResource(id = R.string.common_submit))
        }
    }
}

@Composable
private fun SeparateProgressDialog(
    isProcessingFiles: Boolean,
    processedFileCount: Int,
    totalFileCount: Int,
    onDismissRequest: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val progressDialogState = remember {
        ProgressDialogState()
    }
    LaunchedEffect(key1 = isProcessingFiles) {
        if (!isProcessingFiles) {
            return@LaunchedEffect
        }

        progressDialogState.show()
    }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val completedMessage = stringResource(id = R.string.pdf_separate_completed)
    val openActionLabel = stringResource(id = R.string.pdf_separate_open_file_directory)
    ProgressDialog(
        state = progressDialogState,
        title = { Text(text = stringResource(id = R.string.pdf_separate_processing)) },
        current = processedFileCount,
        total = totalFileCount,
        onComplete = {
            onDismissRequest()

            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = completedMessage,
                    actionLabel = openActionLabel,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite
                )

                if (result == SnackbarResult.ActionPerformed) {
                    context.startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
                }
            }
        }
    )
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        var selectedFile by remember {
            mutableStateOf<UriFile?>(null)
        }
        var isProcessingFiles by remember {
            mutableStateOf(false)
        }
        var processedFileCount by remember {
            mutableIntStateOf(0)
        }
        var isZipped by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = isProcessingFiles) {
            if (!isProcessingFiles) {
                return@LaunchedEffect
            }

            for (i in 1..10) {
                processedFileCount = i
            }
        }

        Content(
            selectedFile = selectedFile,
            onFileChoose = { selectedFile = it },
            requestClearSelectedFile = { selectedFile = null },
            separateFile = {
                processedFileCount = 0
                isProcessingFiles = true
            },
            isProcessingFiles = isProcessingFiles,
            processedFileCount = processedFileCount,
            totalFileCount = 10,
            onDismissProgressDialogRequest = { isProcessingFiles = false },
            isZipped = isZipped,
            onIsZippedChange = { isZipped = it }
        )
    }
}
