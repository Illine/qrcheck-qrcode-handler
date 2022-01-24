package ru.softdarom.qrcheck.qrcode.handler.dao.access

import ru.softdarom.qrcheck.qrcode.handler.model.dto.internal.QRCodeInternalDto

interface QRCodeAccessService {

    fun save(qrcode: QRCodeInternalDto): QRCodeInternalDto

    fun save(qrcodes: Collection<QRCodeInternalDto>): Set<QRCodeInternalDto>

    fun find(externalUserId: Long, content: String): QRCodeInternalDto

    fun find(externalUserId: Long, externalOrderId: Long): QRCodeInternalDto
}