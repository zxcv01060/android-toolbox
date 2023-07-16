package tw.idv.louislee.toolbox.unittest.urlshortener

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import tw.idv.louislee.toolbox.UrlShortenerDto
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerNotSupportedException
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerResolver
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerResolverChain
import tw.idv.louislee.toolbox.urlshortener.UrlShortenerResolverChainFactoryImpl

class UrlShortenerResolverChainFactoryTest {
    @Test
    fun testResolve_shouldBeCallFirstResolverToResolve() = runTest {
        val shortener = UrlShortenerDto(url = "https://www.google.com.tw", html = "")
        val resolvedUrl = "https://www.youtube.com"
        val resolver = mock<UrlShortenerResolver>()
        whenever(resolver.resolve(eq(shortener), any())) doReturn resolvedUrl
        val factory = UrlShortenerResolverChainFactoryImpl(setOf(resolver))
        val chain = factory.create()

        val actualUrl = chain.resolve(shortener)

        Assert.assertEquals(resolvedUrl, actualUrl)
        verify(resolver).resolve(eq(shortener), any())
    }

    @Test
    fun testResolve_shouldBeThrowExceptionWhenNoAnyResolvers() = runTest {
        val factory = UrlShortenerResolverChainFactoryImpl(emptySet())
        val chain = factory.create()

        try {
            chain.resolve(UrlShortenerDto(url = "", html = ""))
        } catch (ignored: UrlShortenerNotSupportedException) {
            return@runTest
        } catch (e: Throwable) {
            Assert.fail("UrlShortenerResolverChainFactory#resolve在沒有resolver可以處理時應拋出UrlShortenerNotSupportedException")
        }
    }

    @Test
    fun testResolve_shouldBeCallSecondaryResolverOnTwiceTimes() = runTest {
        val shortener = UrlShortenerDto(url = "https://www.google.com.tw", html = "")
        val resolvedUrl = "https://www.youtube.com"
        val firstResolver = mock<UrlShortenerResolver>()
        whenever(firstResolver.resolve(eq(shortener), any())) doSuspendableAnswer {
            it.getArgument<UrlShortenerResolverChain>(1)
                .resolve(it.getArgument(0))
        }
        val secondResolver = mock<UrlShortenerResolver>()
        whenever(secondResolver.resolve(eq(shortener), any())) doReturn resolvedUrl
        val factory = UrlShortenerResolverChainFactoryImpl(setOf(firstResolver, secondResolver))
        val chain = factory.create()

        val actualUrl = chain.resolve(shortener)

        Assert.assertEquals(resolvedUrl, actualUrl)
        verify(firstResolver).resolve(eq(shortener), any())
        verify(secondResolver).resolve(eq(shortener), any())
    }
}