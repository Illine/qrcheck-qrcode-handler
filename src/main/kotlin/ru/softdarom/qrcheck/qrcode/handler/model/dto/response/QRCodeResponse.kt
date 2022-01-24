package ru.softdarom.qrcheck.qrcode.handler.model.dto.response

import ru.softdarom.qrcheck.qrcode.handler.model.dto.SavedQRCodeDto

data class QRCodeResponse(
    val qrcodes: Set<SavedQRCodeDto>
)
