package ru.softdarom.qrcheck.qrcode.handler.rest.controller.internal

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.softdarom.qrcheck.qrcode.handler.config.swagger.annotations.ApiGenerateQRCode
import ru.softdarom.qrcheck.qrcode.handler.model.dto.request.GeneratedQRCodeRequest
import ru.softdarom.qrcheck.qrcode.handler.model.dto.response.QRCodeResponse
import ru.softdarom.qrcheck.qrcode.handler.service.QRCodeService
import javax.validation.Valid

@Tag(name = "Внутрений контроллер взаимодействия с QRCodes")
@RestController("internalQRCodeController")
@RequestMapping("/internal/qrcodes")
class QRCodeController(@Autowired private val qrcodeService: QRCodeService) {

    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.qrcode.handler.model.base.RoleType.Ability).API_KEY)")
    @ApiGenerateQRCode
    @PostMapping
    fun generate(
        @RequestHeader(value = "X-Application-Version") version: String,
        @RequestBody @Valid request: GeneratedQRCodeRequest
    ): ResponseEntity<QRCodeResponse> {
        return ResponseEntity.ok(qrcodeService.generateQRCode(request))
    }
}