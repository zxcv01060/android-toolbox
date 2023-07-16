package tw.idv.louislee.toolbox.ui.page.urlshortener

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppErrorMessage
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.component.button.AppIconButton
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlShortenerScreen(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    viewModel: UrlShortenerViewModel = hiltViewModel()
) {
    Content(drawerState = drawerState, requestResolveUrl = viewModel::resolveUrl)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    drawerState: DrawerState,
    requestResolveUrl: suspend (url: String) -> String
) {
    ToolboxTheme {
        AppToolbar(
            title = stringResource(id = R.string.url_shortener_title),
            drawerState = drawerState
        ) {
            Column(
                modifier = Modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var resolvedUrl by remember {
                    mutableStateOf("")
                }
                var error by remember {
                    mutableStateOf<Throwable?>(null)
                }

                AppErrorMessage(error = error)

                val coroutineScope = rememberCoroutineScope()
                UrlShortenerForm {
                    coroutineScope.launch {
                        try {
                            resolvedUrl = requestResolveUrl(it)
                        } catch (e: Throwable) {
                            error = e
                        }
                    }
                }

                ResolvedResult(resolvedUrl = resolvedUrl)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UrlShortenerForm(onSubmit: (String) -> Unit) {
    var url by remember {
        mutableStateOf("")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            label = { Text(text = stringResource(id = R.string.url_shortener_url_field)) },
            trailingIcon = {
                if (url.isBlank()) {
                    return@OutlinedTextField
                }

                AppIconButton(
                    icon = Icons.Filled.Clear,
                    contentDescription = R.string.common_clear
                ) {
                    url = ""
                }
            },
            value = url,
            onValueChange = { url = it }
        )

        Button(onClick = { onSubmit(url.trim()) }) {
            Text(text = stringResource(id = R.string.common_submit))
        }
    }
}

@Composable
private fun ResolvedResult(resolvedUrl: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(id = R.string.url_shortener_resolved_result, resolvedUrl))

        if (resolvedUrl.isBlank()) {
            return@Row
        }
        val clipboardManager = LocalClipboardManager.current
        AppIconButton(
            drawable = R.drawable.baseline_content_copy_24,
            contentDescription = R.string.common_copy
        ) {
            clipboardManager.setText(AnnotatedString(resolvedUrl))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@AppPreview
@Composable
private fun Preview() {
    Content(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        requestResolveUrl = { it }
    )
}
