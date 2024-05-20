package tw.idv.louislee.toolbox.extensions

import java.net.URLEncoder

fun String.encodeUrl(): String = URLEncoder.encode(this, "UTF-8")

fun String.splitFilenameAndExtension(): Pair<String, String> {
    val extensionDotIndex = lastIndexOf('.')
    if (extensionDotIndex == -1) {
        return Pair(this, "")
    }

    return Pair(substring(0, extensionDotIndex), substring(extensionDotIndex + 1))
}
