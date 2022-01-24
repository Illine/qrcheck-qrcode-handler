package ru.softdarom.qrcheck.qrcode.handler.service

import ru.softdarom.qrcheck.qrcode.handler.model.dto.request.GeneratedQRCodeRequest
import ru.softdarom.qrcheck.qrcode.handler.model.dto.response.QRCodeResponse

interface QRCodeService {

    fun generateQRCode(request: GeneratedQRCodeRequest): QRCodeResponse

    fun applyQRCode(encryptedContent: String)

    fun get(externalUserId: Long, externalOrderId: Long): QRCodeResponse
}