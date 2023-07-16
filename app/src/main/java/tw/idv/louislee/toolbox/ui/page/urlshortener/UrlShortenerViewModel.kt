package tw.idv.louislee.toolbox.ui.page.urlshortener

import androidx.lifecycle.ViewModel
import tw.idv.louislee.toolbox.service.UrlShortenerService
import javax.inject.Inject

class UrlShortenerViewModel @Inject constructor(
    private val service: UrlShortenerService
) : ViewModel() {
    suspend fun resolveUrl(url: String): String =
        service.getOriginalUrl(shortenerUrl = url)
}
