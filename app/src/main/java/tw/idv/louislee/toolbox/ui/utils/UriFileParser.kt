package tw.idv.louislee.toolbox.ui.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import tw.idv.louislee.toolbox.model.UriFile

fun Uri.parseUri(context: Context): UriFile = UriFile(
    uri = this,
    filename = getFilename(context),
    inputStreamSupplier = {
        context.contentResolver.openInputStream(this)
            ?: throw IllegalStateException()
    }
)

fun Uri.getFilename(context: Context): String {
    if (scheme == "file") {
        return lastPathSegment ?: throw IllegalStateException()
    }

    context.contentResolver.query(this, null, null, null, null)
        .use {
            if (it == null || !it.moveToFirst()) {
                throw IllegalStateException()
            }

            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index == -1) {
                throw IllegalStateException()
            }

            return it.getString(index)
        }
}
