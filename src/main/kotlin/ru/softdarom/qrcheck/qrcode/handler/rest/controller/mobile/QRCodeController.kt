package ru.softdarom.qrcheck.qrcode.handler.rest.controller.mobile

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.softdarom.qrcheck.qrcode.handler.config.swagger.annotations.ApiApplyQRCode
import ru.softdarom.qrcheck.qrcode.handler.config.swagger.annotations.ApiGetQRCodeByUserIdAndOrderId
import ru.softdarom.qrcheck.qrcode.handler.model.dto.response.QRCodeResponse
import ru.softdarom.qrcheck.qrcode.handler.service.QRCodeService

@Tag(name = "Контроллер взаимодействия с QRCodes frontend-to-backend")
@RestController("mobileQRCodeController")
@RequestMapping("/mobile/qrcodes")
class QRCodeController(@Autowired private val qrcodeService: QRCodeService) {

    @ApiGetQRCodeByUserIdAndOrderId
    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.qrcode.handler.model.base.RoleType.Ability).USER)")
    @GetMapping("/{externalOrderId}")
    fun getAll(
        @RequestHeader(value = "X-Application-Version") version: String,
        authentication: Authentication,
        @PathVariable externalOrderId: Long
    ): ResponseEntity<QRCodeResponse> {
        return ResponseEntity.ok(qrcodeService.get(authentication.principal as Long, externalOrderId))
    }

    @ApiApplyQRCode
    @PreAuthorize("hasRole(T(ru.softdarom.qrcheck.qrcode.handler.model.base.RoleType.Ability).CHECKMAN)")
    @PutMapping("/{encryptedContent}")
    fun apply(@RequestHeader(value = "X-Application-Version") version: String, @PathVariable encryptedContent: String): ResponseEntity<Unit> {
        qrcodeService.applyQRCode(encryptedContent)
        return ResponseEntity.ok().build()
    }
}