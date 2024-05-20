package tw.idv.louislee.toolbox.model

import android.net.Uri
import java.io.InputStream

class UriFile(
    val uri: Uri,
    val filename: String,
    val inputStreamSupplier: () -> InputStream
) {
    val inputStream get() = inputStreamSupplier()
}