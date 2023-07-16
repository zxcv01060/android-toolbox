package tw.idv.louislee.toolbox.unittest.urlshortener

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tw.idv.louislee.toolbox.UrlShortenerDto
import tw.idv.louislee.toolbox.urlshortener.ReurlShortenerResolver
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerException
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerNotSupportedException
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerResolverChain

class ReurlShortenerTest {
    private val resolver = ReurlShortenerResolver()

    @Test
    fun testResolve() = runTest {
        val expectUrl = "https://www.google.com/"
        val shortener = UrlShortenerDto(
            url = "https://reurl.cc/6NrGWV",
            html = generateHtml(expectUrl)
        )
        val chain = mock<UrlShortenerResolverChain>()

        val actualUrl = resolver.resolve(shortener, chain)

        Assert.assertEquals(expectUrl, actualUrl)
    }

    private fun generateHtml(url: String) = """
        <!DOCTYPE html>
        <html>

        <head>
            <title>Google</title>
            <meta property="og:title" content="Google" />
            <meta property="og:description"
                content=".L3eUgb{display:flex;flex-direction:column;height:100%}.o3j99{flex-shrink:0;box-sizing:border-box}.n..." />
            <meta name="robots" content="noindex,nosnippet" />
            <script src="/javascripts/ga.js"></script>
            <script src="/javascripts/pixel.js"></script>
            <script src="/javascripts/redirect.js"></script>
            <noscript><img height="1" width="1" style="display:none" src="https://www.facebook.com/tr?id=1675200226052423&amp;ev=PageView&amp;noscript=1"/></noscript>
        </head>

        <body><input type="hidden" id="url" name="url" value="$url"/></body>

        </html>
    """.trimIndent()

    @Test
    fun testResolve_shouldBeCallNextResolverOnUrlMismatch() = runTest {
        val shortener = UrlShortenerDto(url = "https://www.youtube.com", html = "")
        val chain = mock<UrlShortenerResolverChain>()
        whenever(chain.resolve(shortener)) doReturn "next resolver"

        resolver.resolve(shortener, chain)

        verify(chain).resolve(shortener)
    }

    @Test
    fun testResolve_shouldBeThrowExceptionOnHtmlInvalid() = runTest {
        val shortener = UrlShortenerDto(
            url = "https://reurl.cc/6NrGWV",
            html = """
                <html>
                    <body><h1>hello, world</h1></body>
                </html
            """.trimIndent()
        )
        val chain = mock<UrlShortenerResolverChain>()

        try {
            resolver.resolve(shortener, chain)
        } catch (e: UrlShortenerNotSupportedException) {
            Assert.fail(e.stackTraceToString())
        } catch (ignored: UrlShortenerException) {
            return@runTest
        } catch (e: Throwable) {
            Assert.fail(e.stackTraceToString())
        }
    }
}