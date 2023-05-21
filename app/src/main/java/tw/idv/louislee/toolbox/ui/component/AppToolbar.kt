package tw.idv.louislee.toolbox.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@Composable
fun AppToolbar(title: String, content: @Composable () -> Unit) {
    InternalAppToolbar(title = title, content = content)
}

@Composable
fun AppToolbar(
    title: String,
    onNavigationBack: () -> Unit,
    content: @Composable () -> Unit
) {
    InternalAppToolbar(
        title = title,
        navigationIcon = {
            IconButton(onClick = onNavigationBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.common_back)
                )
            }
        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    title: String,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    InternalAppToolbar(
        title = title,
        navigationIcon = {
            val coroutineScope = rememberCoroutineScope()

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(id = R.string.common_menu)
                )
            }
        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InternalAppToolbar(
    title: String,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = navigationIcon
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

@OptIn(ExperimentalMaterial3Api::class)
@AppPreview
@Composable
private fun MenuPreview() {
    ToolboxTheme {
        AppToolbar(
            title = "標題",
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ) {
            Text(text = "內容")
        }
    }
}
