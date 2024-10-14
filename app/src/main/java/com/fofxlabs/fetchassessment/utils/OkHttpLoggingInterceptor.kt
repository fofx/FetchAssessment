package com.fofxlabs.fetchassessment.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException


class OkHttpLoggingInterceptor : Interceptor {
    var count = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val loggingId = count++

//        Timber.d("$loggingId API Call: ${chain.request().url} payloadSize: ${chain.request().body?.contentLength()}")

        val request: Request = chain.request()
        val t1 = System.nanoTime()
        Timber.d("$loggingId HTTP REQUEST: ${request.method} ${request.url} Payload: ${bodyToString(request)}")

        val response: Response = chain.proceed(request)
        val responseBody: ResponseBody? = response.body
        val responseBodyString: String = response.body?.string() ?: ""

        val newResponse: Response = response.newBuilder().body(responseBodyString.toByteArray().toResponseBody(responseBody?.contentType())).build()
        val t2 = System.nanoTime()
        val responseTime = (t2-t1)/ 1e6 // in ms
        Timber.d("$loggingId HTTP RESPONSE: ${response.code} ${response.request.url} in ${responseTime}ms.\nResponse payload: $responseBodyString")
        return newResponse
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy: Request = request.newBuilder().build()
            val buffer = Buffer()
            copy.body?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "Failed to translate the body content to a string"
        }
    }
}