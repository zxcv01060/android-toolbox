package tw.idv.louislee.toolbox.unittest.service

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tw.idv.louislee.toolbox.UrlShortenerDto
import tw.idv.louislee.toolbox.service.JsoupService
import tw.idv.louislee.toolbox.service.UrlShortenerServiceImpl
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerResolverChain
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerResolverChainFactory

class UrlShortenerServiceTest {
    @Mock
    lateinit var factory: UrlShortenerResolverChainFactory

    @Mock
    lateinit var jsoupService: JsoupService

    @Mock
    lateinit var chain: UrlShortenerResolverChain

    private lateinit var service: UrlShortenerServiceImpl

    @Before
    fun initMock() {
        MockitoAnnotations.openMocks(this)
        whenever(factory.create()) doReturn chain

        this.service = UrlShortenerServiceImpl(factory, jsoupService)
    }

    @Test
    fun testGetOriginalUrlShouldBeCallBuilderResolveUrl() = runTest {
        val url = "https://www.google.com.tw"
        val html = "mock html"
        val shortener = UrlShortenerDto(url = url, html = html)
        whenever(jsoupService.getHtml(eq(url))) doReturn html
        val originalUrl = "https://www.youtube.com"
        whenever(chain.resolve(eq(shortener))) doReturn originalUrl

        Assert.assertEquals(originalUrl, service.getOriginalUrl(url))
        verify(factory).create()
        verify(chain).resolve(eq(shortener))
    }
}