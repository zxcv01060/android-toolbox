package tw.idv.louislee.toolbox.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    title: String,
    onNavigationBack: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    if (onNavigationBack != null) {
                        IconButton(onClick = onNavigationBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.common_back)
                            )
                        }
                    }
                }
            )
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        AppToolbar(title = "標題") {
            Text(text = "內容")
        }
    }
}

@AppPreview
@Composable
private fun NavigationBackPreview() {
    ToolboxTheme {
        AppToolbar(title = "標題", onNavigationBack = {}) {
            Text(text = "內容")
        }
    }
}
