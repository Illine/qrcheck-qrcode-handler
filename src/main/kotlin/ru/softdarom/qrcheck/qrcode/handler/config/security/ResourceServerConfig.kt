package ru.softdarom.qrcheck.qrcode.handler.config.security

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import ru.softdarom.qrcheck.qrcode.handler.model.base.RoleType

@Configuration
class ResourceServerConfig(
    @Qualifier("qrCheckAuthenticationProvider") val authenticationProvider: AuthenticationProvider,
    @Qualifier("qrCheckAuthenticationEntryPoint") val authenticationEntryPoint: AuthenticationEntryPoint,
    @Qualifier("qrCheckAccessDeniedHandler") val accessDeniedHandler: AccessDeniedHandler,
    @Qualifier("qrCheckApiKeyAuthorizationFilter") val apiKeyAuthorizationFilter: AbstractPreAuthenticatedProcessingFilter
) : ResourceServerConfigurerAdapter() {

    private val DISABLED_RESOURCE_ID: String? = null

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
            .formLogin()
                .disable()
            .httpBasic()
                .disable()
            .csrf()
                .disable()
            .anonymous()
                .disable()
            .authorizeRequests { request ->
                request
                    .antMatchers("/internal/**").hasAnyRole(*RoleType.getInternalAbilityRoles())
                    .antMatchers("/mobile/**").hasAnyRole(*RoleType.getMobileAbilityRoles())
            }
            .addFilter(apiKeyAuthorizationFilter)
            .authenticationProvider(authenticationProvider)
            .exceptionHandling{ handlerConfigurer:ExceptionHandlingConfigurer<HttpSecurity> ->
                handlerConfigurer
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            }
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // @formatter:on
    }

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources
            .resourceId(DISABLED_RESOURCE_ID)
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
    }
}