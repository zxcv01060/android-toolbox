package tw.idv.louislee.toolbox.ui.component.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@Composable
fun AppCheckbox(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)

        Text(modifier = Modifier.clickable { onCheckedChange(!isChecked) }, text = label)
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        var isChecked by remember {
            mutableStateOf(false)
        }

        AppCheckbox(label = "文字", isChecked = isChecked, onCheckedChange = { isChecked = it })
    }
}
