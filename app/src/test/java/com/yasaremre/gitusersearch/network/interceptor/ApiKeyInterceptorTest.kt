package com.yasaremre.gitusersearch.network.interceptor

import com.yasaremre.gitusersearch.BuildConfig
import io.mockk.*
import junit.framework.TestCase
import okhttp3.Interceptor
import okhttp3.Request
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ApiKeyInterceptorTest : TestCase() {

    lateinit var interceptor: ApiKeyInterceptor

    @Before
    public override fun setUp() {
        super.setUp()
        interceptor = ApiKeyInterceptor()
    }

    @After
    public override fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test if interceptor adds authorization header param when it is not present`() {
        val request = Request.Builder().url(BuildConfig.API_BASE_URL).build()

        val proceededRequest = proceedWithInterceptor(request)
        Assert.assertTrue(
            proceededRequest.headers(name = ApiKeyInterceptor.AUTHORIZATION_HEADER_KEY).isNotEmpty()
        )
        Assert.assertEquals("Token ${BuildConfig.API_KEY}",
            proceededRequest.headers(name = ApiKeyInterceptor.AUTHORIZATION_HEADER_KEY)[0]
        )
    }

    @Test
    fun `test if interceptor skips authorization header param when base url does not match`() {
        val request = Request.Builder().url("https://www.someurl.com").build()

        val proceededRequest = proceedWithInterceptor(request)
        Assert.assertTrue(proceededRequest.headers(name = ApiKeyInterceptor.AUTHORIZATION_HEADER_KEY).isEmpty())
    }

    private fun proceedWithInterceptor(request: Request): Request {
        val chain: Interceptor.Chain = mockk(relaxed = true)
        every { chain.request() } returns request

        interceptor.intercept(chain)

        val requestSlot = slot<Request>()
        verify { chain.proceed(capture(requestSlot)) }
        return requestSlot.captured
    }
}