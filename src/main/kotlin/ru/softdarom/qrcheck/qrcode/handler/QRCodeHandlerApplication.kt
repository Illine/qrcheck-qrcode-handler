package ru.softdarom.qrcheck.qrcode.handler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import ru.softdarom.qrcheck.qrcode.handler.config.properties.LogbookProperties
import ru.softdarom.qrcheck.qrcode.handler.config.properties.OpenApiProperties
import ru.softdarom.security.oauth2.config.property.ApiKeyProperties

@EnableFeignClients
@EnableCaching
@EnableWebSecurity
@EnableConfigurationProperties(
    OpenApiProperties::class,
    LogbookProperties::class,
    ApiKeyProperties::class
)
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
class QRCodeHandlerApplication

fun main(args: Array<String>) {
    runApplication<QRCodeHandlerApplication>(*args)
}