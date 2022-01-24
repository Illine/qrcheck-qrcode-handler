package ru.softdarom.qrcheck.qrcode.handler.dao.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.softdarom.qrcheck.qrcode.handler.dao.entity.QRCodeEntity

interface QRCodeRepository : JpaRepository<QRCodeEntity, Long> {

    fun findByExternalUserIdAndContent(externalUserId: Long, content: String): QRCodeEntity?

    fun findByExternalUserIdAndExternalOrderId(externalUserId: Long, externalOrderId: Long): QRCodeEntity?

}