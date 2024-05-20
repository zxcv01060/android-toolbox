package tw.idv.louislee.toolbox.ui.page.pdf

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.AppToolbar
import tw.idv.louislee.toolbox.ui.page.pdf.tab.separate.PdfSeparateTab
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme

private const val TAB_SEPARATOR = 0

@Composable
fun PdfScreen(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    Content(drawerState = drawerState)
}

@Composable
private fun Content(drawerState: DrawerState) {
    AppToolbar(
        title = stringResource(id = R.string.pdf_title),
        drawerState = drawerState
    ) {
        var selectedTabIndex by remember {
            mutableIntStateOf(TAB_SEPARATOR)
        }

        Column {
            PdfTabRow(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it })

            when (selectedTabIndex) {
                TAB_SEPARATOR -> PdfSeparateTab()
                else -> Unit
            }
        }
    }
}

@Composable
private fun PdfTabRow(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        Tab(
            selected = selectedTabIndex == TAB_SEPARATOR,
            text = { Text(text = stringResource(id = R.string.pdf_separate_file_title)) },
            onClick = { onTabSelected(TAB_SEPARATOR) }
        )
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        Content(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed))
    }
}
