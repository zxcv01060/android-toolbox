package tw.idv.louislee.toolbox.ui.preview

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@OptIn(ExperimentalMaterial3Api::class)
class DrawerValuePreviewParameterProvider : PreviewParameterProvider<DrawerValue> {
    override val values: Sequence<DrawerValue>
        get() = DrawerValue.entries.asSequence()
}