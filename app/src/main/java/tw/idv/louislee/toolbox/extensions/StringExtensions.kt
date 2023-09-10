package tw.idv.louislee.toolbox.extensions

import java.net.URLEncoder

fun String.encodeUrl(): String = URLEncoder.encode(this, "UTF-8")
