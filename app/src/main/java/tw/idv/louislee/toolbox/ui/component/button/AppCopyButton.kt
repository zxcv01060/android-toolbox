package tw.idv.louislee.toolbox.ui.component.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import tw.idv.louislee.toolbox.R

@Composable
fun AppCopyButton(copyText: String, doNotCopyIfBlank: Boolean = true) {
    val clipboard = LocalClipboardManager.current

    AppIconButton(
        drawable = R.drawable.baseline_content_copy_24,
        contentDescription = R.string.common_copy
    ) {
        if (copyText.isBlank() && doNotCopyIfBlank) {
            return@AppIconButton
        }

        clipboard.setText(AnnotatedString(copyText))
    }
}
