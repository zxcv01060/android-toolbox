package tw.idv.louislee.toolbox.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

interface JsoupService {
    suspend fun getHtml(url: String): String
}

internal class JsoupServiceImpl @Inject constructor() : JsoupService {
    override suspend fun getHtml(url: String): String = withContext(Dispatchers.IO) {
        Jsoup.connect(url)
            .get()
            .outerHtml()
    }
}
