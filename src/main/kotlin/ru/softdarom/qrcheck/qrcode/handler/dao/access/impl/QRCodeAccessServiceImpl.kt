package ru.softdarom.qrcheck.qrcode.handler.dao.access.impl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.softdarom.qrcheck.qrcode.handler.dao.access.QRCodeAccessService
import ru.softdarom.qrcheck.qrcode.handler.dao.entity.QRCodeEntity
import ru.softdarom.qrcheck.qrcode.handler.dao.repository.QRCodeRepository
import ru.softdarom.qrcheck.qrcode.handler.exception.NotFoundException
import ru.softdarom.qrcheck.qrcode.handler.model.dto.internal.QRCodeInternalDto

@Service
class QRCodeAccessServiceImpl(@Autowired val qrcodeRepository: QRCodeRepository) : QRCodeAccessService {

    private val log = LoggerFactory.getLogger("ACCESS-SERVICE")

    @Transactional
    override fun save(qrcode: QRCodeInternalDto): QRCodeInternalDto {
        log.debug("Save a qrcode: {}", qrcode)
        val entity = QRCodeEntity(qrcode)
        return QRCodeInternalDto(qrcodeRepository.save(entity))
    }

    @Transactional
    override fun save(qrcodes: Collection<QRCodeInternalDto>): Set<QRCodeInternalDto> {
        log.debug("Save a qrcodes: {}", qrcodes)
        val entities =
            qrcodes
                .map { QRCodeEntity(it) }
                .toSet()
        return qrcodeRepository.saveAll(entities).map { QRCodeInternalDto(it) }.toSet()
    }

    @Transactional
    override fun find(externalUserId: Long, content: String): QRCodeInternalDto {
        log.debug("Find a qrcode by externalUserId ('{}') and content ('{}')", externalUserId, content)
        val entity = qrcodeRepository.findByExternalUserIdAndContent(externalUserId, content)
        return entity?.let { QRCodeInternalDto(it) } ?: throw NotFoundException("QRCode not found!")
    }

    override fun find(externalUserId: Long, externalOrderId: Long): QRCodeInternalDto {
        log.debug("Find a qrcode by externalUserId ({}) and externalOrderId ()")
        val entity = qrcodeRepository.findByExternalUserIdAndExternalOrderId(externalUserId, externalOrderId)
        return entity?.let { QRCodeInternalDto(it) } ?: throw NotFoundException("QRCode not found!")
    }
}