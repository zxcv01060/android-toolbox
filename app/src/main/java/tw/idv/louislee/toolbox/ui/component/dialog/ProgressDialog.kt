package tw.idv.louislee.toolbox.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@Composable
fun ProgressDialog(
    state: ProgressDialogState,
    title: @Composable () -> Unit,
    current: Int,
    total: Int,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!state.isShowing) {
        return
    }

    if (state.isShowing && current >= total) {
        state.dismiss()
        onComplete()
        return
    }

    AlertDialog(
        modifier = modifier,
        title = title,
        text = {
            Column {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { current.toFloat() / total.toFloat() }
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "$current / $total"
                )
            }
        },
        onDismissRequest = {},
        confirmButton = {}
    )
}

class ProgressDialogState {
    var isShowing: Boolean by mutableStateOf(false)
        private set

    fun show() {
        isShowing = true
    }

    internal fun dismiss() {
        isShowing = false
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        var current by remember {
            mutableIntStateOf(0)
        }
        var isComplete by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = Unit) {
            for (i in 1..100) {
                current++
                delay(1000)
            }
        }

        val state = remember {
            ProgressDialogState()
        }
        state.show()
        ProgressDialog(
            state = state,
            title = { Text(text = "處理中...") },
            current = current,
            total = 100,
            onComplete = { isComplete = true }
        )

        if (isComplete) {
            Text(text = "完成!")
        }
    }
}
