package ru.softdarom.qrcheck.qrcode.handler.config

import feign.codec.Encoder
import feign.form.spring.SpringFormEncoder
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class FeignConfig {

    @Bean
    fun multipartFormEncoder(): Encoder? {
        return SpringFormEncoder(SpringEncoder { HttpMessageConverters(RestTemplate().messageConverters) })
    }
}