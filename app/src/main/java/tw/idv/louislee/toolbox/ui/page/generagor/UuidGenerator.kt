package tw.idv.louislee.toolbox.ui.page.generagor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tw.idv.louislee.toolbox.R
import tw.idv.louislee.toolbox.ui.AppPreview
import tw.idv.louislee.toolbox.ui.component.button.AppCopyButton
import java.util.UUID

@Composable
fun UuidGenerator(generateUuid: () -> String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val uuidHistories = remember {
            mutableStateListOf<String>()
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Button(onClick = { uuidHistories.add(generateUuid()) }) {
                Text(text = stringResource(id = R.string.generator_generate))
            }
        }

        LazyColumn(reverseLayout = true) {
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
    UuidGenerator {
        UUID.randomUUID().toString()
    }
}
