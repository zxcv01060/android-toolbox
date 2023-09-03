package tw.idv.louislee.toolbox.ui.page.generator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.button.AppCopyButton
import tw.idv.louislee.toolbox.ui.theme.ToolboxTheme
import java.util.UUID

@Composable
fun UuidGenerator(generateUuid: () -> String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val uuidHistories = remember {
            mutableStateListOf<String>()
        }
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        Button(onClick = {
            uuidHistories.add(generateUuid())
            coroutineScope.launch {
                lazyListState.scrollToItem(index = uuidHistories.lastIndex)
            }
        }) {
            Text(text = stringResource(id = R.string.generator_generate))
        }

        LazyColumn(state = lazyListState, reverseLayout = true) {
            itemsIndexed(uuidHistories) { i, uuid ->
                UuidRow(id = i + 1, uuid = uuid)
            }
        }
    }
}

@Composable
private fun UuidRow(id: Int, uuid: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$id. $uuid")

        if (uuid.isNotBlank()) {
            AppCopyButton(copyText = uuid)
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    ToolboxTheme {
        UuidGenerator {
            UUID.randomUUID().toString()
        }
    }
}
