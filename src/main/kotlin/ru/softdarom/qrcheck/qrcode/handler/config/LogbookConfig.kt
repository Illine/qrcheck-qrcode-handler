package ru.softdarom.qrcheck.qrcode.handler.config

import brave.Tracer
import ch.qos.logback.classic.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.logbook.*
import ru.softdarom.qrcheck.orders.util.LogbackHelper
import ru.softdarom.qrcheck.qrcode.handler.config.properties.LogbookProperties

@Configuration
class LogbookConfig {

    @Bean
    fun writer(properties: LogbookProperties): HttpLogWriter {
        return QRCheckHttpLogWriter(properties)
    }

    @Bean
    fun correlationId(tracer: Tracer): CorrelationId {
        return SleuthCorrelationId(tracer)
    }

    class QRCheckHttpLogWriter(properties: LogbookProperties) : HttpLogWriter {
        private val logger: Logger

        override fun isActive(): Boolean {
            return logger.isInfoEnabled
        }

        override fun write(precorrelation: Precorrelation, request: String) {
            logger.info(request)
        }

        override fun write(correlation: Correlation, response: String) {
            logger.info(response)
        }

        init {
            logger = LogbackHelper.getLogger(properties.name)
        }
    }

    class SleuthCorrelationId internal constructor(private val tracer: Tracer) : CorrelationId {
        override fun generate(request: HttpRequest): String {
            return tracer.currentSpan()?.context()?.traceIdString() ?: "[Span not found]"
        }
    }
}