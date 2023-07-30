package tw.idv.louislee.toolbox.ui.preview

import androidx.compose.material3.DrawerValue
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class DrawerValuePreviewParameterProvider : PreviewParameterProvider<DrawerValue> {
    override val values: Sequence<DrawerValue>
        get() = DrawerValue.entries.asSequence()
}