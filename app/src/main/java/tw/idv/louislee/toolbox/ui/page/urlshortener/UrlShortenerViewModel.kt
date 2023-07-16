package tw.idv.louislee.toolbox.ui.page.urlshortener

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tw.idv.louislee.toolbox.service.UrlShortenerService
import javax.inject.Inject

//interface UrlShortenerViewModel {
//    suspend fun resolveUrl(url: String): String
//}

//@HiltViewModel
//class UrlShortenerViewModelImpl @Inject constructor(
//    private val service: UrlShortenerService
//) : ViewModel(), UrlShortenerViewModel {
//    override suspend fun resolveUrl(url: String): String =
//        service.getOriginalUrl(shortenerUrl = url)
//}

@HiltViewModel
class UrlShortenerViewModel @Inject constructor(
    private val service: UrlShortenerService
) : ViewModel() {
    suspend fun resolveUrl(url: String): String =
        service.getOriginalUrl(shortenerUrl = url)
}
