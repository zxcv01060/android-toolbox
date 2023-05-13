package tw.idv.louislee.toolbox.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview


@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Preview(
    group = "手機",
    name = "手機(Dark Mode)",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    group = "手機",
    name = "手機",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    group = "平板",
    name = "平板(Dark Mode)",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = "spec:width=1280dp,height=800dp,dpi=480"
)
@Preview(
    group = "平板",
    name = "平板",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = "spec:width=1280dp,height=800dp,dpi=480"
)
annotation class AppPreview()
