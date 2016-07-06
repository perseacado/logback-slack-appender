package com.github.perseacado.logback.slack

import ch.qos.logback.classic.html.HTMLLayout
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Layout
import ch.qos.logback.core.UnsynchronizedAppenderBase

/**
 * @author Marco Eigletsberger, 06.07.16.
 */
class SlackAppender : UnsynchronizedAppenderBase<ILoggingEvent>() {
    private val layout: Layout<ILoggingEvent> = HTMLLayout()
    var token: String = "<token not set>"
    var channel: String = "logging"
    var username: String = "logger"

    override fun append(event: ILoggingEvent) {
        try {
            val message = layout.doLayout(event)
            SlackClient.pushToChannel(token, channel, username, message)
        } catch(e: Exception) {
            addError("could not push log message to Slack", e)
        }
    }
}