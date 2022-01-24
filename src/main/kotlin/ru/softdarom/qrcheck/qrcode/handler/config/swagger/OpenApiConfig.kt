package ru.softdarom.qrcheck.qrcode.handler.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.softdarom.qrcheck.qrcode.handler.config.properties.OpenApiProperties
import ru.softdarom.security.oauth2.config.property.ApiKeyProperties

@Configuration
class OpenApiConfig(@Autowired private val openApiProperties: OpenApiProperties) {

    companion object {
        const val API_KEY_SECURITY_NAME = "api-key"
        const val BEARER_SECURITY_NAME = "bearer"
    }

    private val BEARER_TOKEN_HEADER_NAME = "Authorization"
    private val BEARER_TOKEN_DESCRIPTION = "Аутентификация через oAuth 2.0"
    private val API_KEY_DESCRIPTION = "Аутентификация через ApiKey"
    private val LICENCE = "Лицензия API"

    @Bean
    fun customOpenAPI(info: Info, components: Components): OpenAPI {
        return OpenAPI()
            .components(components)
            .info(info)
    }

    @Bean
    fun components(apiKeyProperties: ApiKeyProperties): Components? {
        return Components()
            .addSecuritySchemes(
                BEARER_SECURITY_NAME,
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme(BEARER_SECURITY_NAME)
                    .`in`(SecurityScheme.In.HEADER)
                    .name(BEARER_TOKEN_HEADER_NAME)
                    .description(BEARER_TOKEN_DESCRIPTION)
            )
            .addSecuritySchemes(
                API_KEY_SECURITY_NAME,
                SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .`in`(SecurityScheme.In.HEADER)
                    .name(apiKeyProperties.headerName)
                    .description(API_KEY_DESCRIPTION)
            )
    }

    @Bean
    fun info(license: License, contact: Contact): Info? {
        return Info()
            .title(openApiProperties.title)
            .version(openApiProperties.version)
            .description(openApiProperties.description)
            .license(license)
            .contact(contact)
    }

    @Bean
    fun license(): License {
        return License()
            .name(LICENCE)
            .url(openApiProperties.licenceUrl)
    }

    @Bean
    fun contact(): Contact {
        return Contact()
            .name(openApiProperties.ownerName)
            .email(openApiProperties.ownerEmail)
            .url(openApiProperties.ownerUrl)
    }
}