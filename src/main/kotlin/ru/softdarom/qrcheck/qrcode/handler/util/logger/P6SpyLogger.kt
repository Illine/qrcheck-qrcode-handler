package ru.softdarom.qrcheck.orders.util.logger

import com.p6spy.engine.spy.appender.Slf4JLogger
import org.slf4j.LoggerFactory
import org.springframework.util.ReflectionUtils

class P6SpyLogger : Slf4JLogger() {

    private val logger = LoggerFactory.getLogger("SQL")

    init {
        reflectionSetLogger()
    }

    private fun reflectionSetLogger() {
        val logField = ReflectionUtils.findField(Slf4JLogger::class.java, "log")!!
        ReflectionUtils.makeAccessible(logField)
        ReflectionUtils.setField(logField, this, logger)
    }
}