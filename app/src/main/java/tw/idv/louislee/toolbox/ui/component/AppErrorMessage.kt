package tw.idv.louislee.toolbox.ui.component

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.exception.AppException

@Composable
fun AppErrorMessage(error: Throwable?) {
    var isShowing by remember(error) {
        mutableStateOf(error != null)
    }

    if (error == null || !isShowing) {
        return
    }

    Log.e("Toolbox", "發生錯誤", error)

    val defaultMessage = "發生錯誤，請聯繫開發人員處理"
    val message = if (error is AppException) {
        error.message ?: defaultMessage
    } else {
        defaultMessage
    }

    AlertDialog(
        title = { Text(text = stringResource(id = R.string.common_error)) },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = { isShowing = false }) {
                Text(text = stringResource(id = R.string.common_ok))
            }
        },
        onDismissRequest = { isShowing = false }
    )
}
