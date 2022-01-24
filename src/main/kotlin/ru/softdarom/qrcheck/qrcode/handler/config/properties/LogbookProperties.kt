package ru.softdarom.qrcheck.qrcode.handler.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty

@ConstructorBinding
@Validated
@ConfigurationProperties("logbook.logger")
data class LogbookProperties(
    @NotEmpty
    val name: String
)