package tw.idv.louislee.toolbox.ui.component.button

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import tw.idv.louislee.toolbox.R

@Composable
fun AppCopyButton(
    copyText: String,
    @StringRes buttonText: Int = R.string.common_copy,
    doNotCopyIfBlank: Boolean = true
) {
    AppCopyButton(
        buttonText = stringResource(id = buttonText),
        copyText = copyText,
        doNotCopyIfBlank = doNotCopyIfBlank
    )
}

@Composable
fun AppCopyButton(buttonText: String, copyText: String, doNotCopyIfBlank: Boolean = true) {
    val clipboard = LocalClipboardManager.current

    Button(onClick = { copy(clipboard, copyText, doNotCopyIfBlank) }) {
        Text(text = buttonText)
    }
}

private fun copy(
    clipboard: ClipboardManager,
    copyText: String,
    doNotCopyIfBlank: Boolean
) {
    if (copyText.isBlank() && doNotCopyIfBlank) {
        return
    }

    clipboard.setText(AnnotatedString(copyText))
}

@Composable
fun AppCopyButton(copyText: String, doNotCopyIfBlank: Boolean = true) {
    val clipboard = LocalClipboardManager.current

    AppIconButton(
        drawable = R.drawable.baseline_content_copy_24,
        contentDescription = R.string.common_copy,
        onClick = { copy(clipboard, copyText, doNotCopyIfBlank) }
    )
}
