package ru.softdarom.qrcheck.qrcode.handler.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class WebSecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers(
                "/",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-api/**"
            )
            .antMatchers(
                "/actuator/health/**",
                "/actuator/prometheus/**"
            )
    }
}