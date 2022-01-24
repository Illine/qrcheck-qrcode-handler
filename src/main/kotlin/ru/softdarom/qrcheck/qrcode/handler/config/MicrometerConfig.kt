package ru.softdarom.qrcheck.qrcode.handler.config

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MicrometerConfig {

    @Bean
    fun metricsCustomTags(@Value("\${spring.application.name}") applicationName: String): MeterRegistryCustomizer<MeterRegistry> {
        return MeterRegistryCustomizer { registry: MeterRegistry -> registry.config().commonTags("application", applicationName) }
    }
}