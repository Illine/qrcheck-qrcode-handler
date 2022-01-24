package ru.softdarom.qrcheck.orders.util

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory

class LogbackHelper {

    companion object {
        fun getLogger(name: String): Logger {
            return loggerContext.getLogger(name)
        }

        fun getLogger(name: String, level: Level): Logger {
            val logger = getLogger(name)
            logger.level = level
            return logger
        }

        fun switchLoggerLevel(name: String, level: Level) {
            loggerContext.getLogger(name).level = level
        }

        private val loggerContext: LoggerContext
            get() = LoggerFactory.getILoggerFactory() as LoggerContext
    }


}