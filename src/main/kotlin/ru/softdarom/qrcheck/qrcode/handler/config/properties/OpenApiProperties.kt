package ru.softdarom.qrcheck.qrcode.handler.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty

@ConstructorBinding
@Validated
@ConfigurationProperties("springdoc.info")
data class OpenApiProperties(
    @NotEmpty
    val title: String,
    @NotEmpty
    val description: String,
    @NotEmpty
    val version: String,
    @NotEmpty
    val licenceUrl: String,
    @NotEmpty
    val ownerName: String,
    @NotEmpty
    val ownerUrl: String,
    @NotEmpty
    val ownerEmail: String
)