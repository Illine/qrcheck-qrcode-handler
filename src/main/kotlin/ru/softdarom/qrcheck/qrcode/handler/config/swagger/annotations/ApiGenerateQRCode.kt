package ru.softdarom.qrcheck.qrcode.handler.config.swagger.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import ru.softdarom.qrcheck.qrcode.handler.config.swagger.OpenApiConfig
import ru.softdarom.qrcheck.qrcode.handler.model.dto.response.QRCodeResponse
import ru.softdarom.security.oauth2.dto.response.ErrorResponse

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Operation(
    summary = "Генерация QRCode",
    security = [SecurityRequirement(name = OpenApiConfig.API_KEY_SECURITY_NAME)],
    responses = [ApiResponse(
        responseCode = "200",
        description = "QRCode сгенерирован",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = QRCodeResponse::class))]
    ), ApiResponse(
        responseCode = "401",
        description = "Отсутствует авторизация",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))]
    ), ApiResponse(
        responseCode = "403",
        description = "Неавторизованный запрос",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))]
    ), ApiResponse(
        responseCode = "500",
        description = "Неизвестная ошибка",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class))]
    )]
)
annotation class ApiGenerateQRCode