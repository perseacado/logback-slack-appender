package com.github.perseacado.logback.slack

import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.impl.client.HttpClients
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.Charset

/**
 * @author Marco Eigletsberger, 06.07.16.
 */
object SlackClient {

    private val API_BASE_URI = "https://slack.com"
    private val API_POST_MESSAGE = API_BASE_URI + "/api/chat.postMessage"
    private val CHARSET = Charset.forName("UTF-8")
    private val PARAM_TOKEN = "token"
    private val PARAM_USERNAME = "username"
    private val PARAM_CHANNEL = "channel"
    private val PARAM_ATTACHMENTS = "attachments"
    private val httpclient = HttpClients.createMinimal()

    fun pushToChannel(token: String, channel: String, username: String, message: String) {
        val entity = MultipartEntityBuilder.create()
            .setCharset(CHARSET)
            .addTextBody(PARAM_TOKEN, token)
            .addTextBody(PARAM_USERNAME, username)
            .addTextBody(PARAM_CHANNEL, channel)
            .addTextBody(PARAM_ATTACHMENTS, message, ContentType.APPLICATION_JSON)
            .build()
        execute(API_POST_MESSAGE, entity)
    }

    @Throws(IOException::class)
    private fun execute(uri: String, httpEntity: HttpEntity) {
        val httpPost = HttpPost(uri)
        httpPost.entity = httpEntity

        httpclient.execute(httpPost).use { response ->
            val outputStream = ByteArrayOutputStream()
            response.entity.writeTo(outputStream)
        }
    }
}