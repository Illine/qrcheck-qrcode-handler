package ru.softdarom.qrcheck.orders.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object JsonHelper {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private const val DATE_PATTERN = "dd.MM.yyyy"
    private const val TIME_PATTERN = "HH:mm:ss"
    private val DATE_TIME_PATTERN = String.format("%s %s", DATE_PATTERN, TIME_PATTERN)

    private val DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN)
    private val DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN)
    private val DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)

    private val MAPPER = ObjectMapper()

    init {
        defaultConfigure()
    }

    fun <T> asJson(`object`: T): String {
        return try {
            MAPPER.writeValueAsString(`object`)
        } catch (e: JsonProcessingException) {
            logger.error("Error map to json! Return 'unknown'!", e)
            "unknown"
        }
    }

    private fun defaultConfigure() {
        MAPPER.registerModule(defaultTimeModule())
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    private fun defaultTimeModule(): JavaTimeModule? {
        val module = JavaTimeModule()
        module.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(DEFAULT_DATE_TIME_FORMATTER))
        module.addSerializer(LocalDate::class.java, LocalDateSerializer(DEFAULT_DATE_FORMATTER))
        module.addSerializer(LocalTime::class.java, LocalTimeSerializer(DEFAULT_TIME_FORMATTER))
        return module
    }
}