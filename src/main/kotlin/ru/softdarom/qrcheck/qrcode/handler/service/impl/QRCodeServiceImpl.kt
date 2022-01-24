package ru.softdarom.qrcheck.qrcode.handler.service.impl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.softdarom.qrcheck.qrcode.handler.dao.access.QRCodeAccessService
import ru.softdarom.qrcheck.qrcode.handler.exception.QRCOdeApplyException
import ru.softdarom.qrcheck.qrcode.handler.model.base.QRCodeType
import ru.softdarom.qrcheck.qrcode.handler.model.dto.SavedQRCodeDto
import ru.softdarom.qrcheck.qrcode.handler.model.dto.internal.QRCodeInternalDto
import ru.softdarom.qrcheck.qrcode.handler.model.dto.request.GeneratedQRCodeRequest
import ru.softdarom.qrcheck.qrcode.handler.model.dto.response.QRCodeResponse
import ru.softdarom.qrcheck.qrcode.handler.service.CryptoService
import ru.softdarom.qrcheck.qrcode.handler.service.ImageSaverService
import ru.softdarom.qrcheck.qrcode.handler.service.QRCodeService
import java.time.LocalDateTime
import java.util.*

@Service
class QRCodeServiceImpl(
    @Autowired private val cryptoService: CryptoService,
    @Autowired private val qrcodeAccessService: QRCodeAccessService,
    @Autowired private val imageSaverService: ImageSaverService
) : QRCodeService {

    private val log = LoggerFactory.getLogger("SERVICE")

    override fun generateQRCode(request: GeneratedQRCodeRequest): QRCodeResponse {
        log.info("Generate a qrcode by externalUserId: {} and qrcodeData: {}", request.externalUserId, request.generatedQRCodeData)
        val qrcodes = request.generatedQRCodeData.map {
            val salt = generateSalt()
            val content = generateContent(request.externalUserId, salt)
            val url = imageSaverService.saveQRCodeContent(content)
            buildQRCode(request.externalUserId, it.externalEventOrOptionId, it.externalOrderId, salt, content, it.type, url)
        }.toSet()

        val savedQRCodes =
            qrcodeAccessService.save(qrcodes)
                .map { SavedQRCodeDto(it) }.toSet()

        return QRCodeResponse(savedQRCodes)
    }

    override fun applyQRCode(encryptedContent: String) {
        log.info("A qrcode (encrypted content: '{}') will apply", encryptedContent)
        val parsedContent = cryptoService.decrypt(encryptedContent).split(";")
        log.info("Decrypted qrcode: {}", parsedContent.toString())
        val savedQRCode = qrcodeAccessService.find(parsedContent.get(0).toLong(), encryptedContent)
        if (savedQRCode.applied) {
            throw QRCOdeApplyException("QRCode has been applied already! Data applied: ${savedQRCode.dateApplied}")
        }
        apply(savedQRCode)
        qrcodeAccessService.save(savedQRCode)
    }

    override fun get(externalUserId: Long, externalOrderId: Long): QRCodeResponse {
        log.info("Get a qrcode by externalUserId ({}) and externalOrderId ({})", externalUserId, externalOrderId)
        val savedQRCodes = setOf(qrcodeAccessService.find(externalUserId, externalOrderId).let { SavedQRCodeDto(it) })
        return QRCodeResponse(savedQRCodes)
    }

    private fun generateSalt() = UUID.randomUUID().toString().replace("-", "")

    private fun generateContent(externalUserId: Long, salt: String): String {
        val content = "$externalUserId;$salt"
        log.debug("Generated content: {}", content)
        val cryptContent = cryptoService.encrypt(content)
        log.debug("Crypt content: {}", cryptContent)
        return cryptContent
    }

    private fun buildQRCode(
        externalUserId: Long,
        externalEventId: Long,
        externalOrderId: Long,
        salt: String,
        content: String,
        type: QRCodeType,
        url: String
    ): QRCodeInternalDto =
        if (type == QRCodeType.EVENT)
            buildEventQRCode(externalUserId, externalEventId, externalOrderId, salt, content, url)
        else
            buildOptionalQRCode(externalUserId, externalEventId, externalOrderId, salt, content, url)


    private fun buildEventQRCode(
        externalUserId: Long,
        externalEventId: Long,
        externalOrderId: Long,
        salt: String,
        content: String,
        url: String
    ): QRCodeInternalDto {
        return QRCodeInternalDto(
            externalUserId = externalUserId,
            externalEventId = externalEventId,
            externalOrderId = externalOrderId,
            type = QRCodeType.EVENT,
            content = content,
            salt = salt,
            url = url
        )
    }

    private fun buildOptionalQRCode(
        externalUserId: Long,
        externalOptionId: Long,
        externalOrderId: Long,
        salt: String,
        content: String,
        url: String
    ): QRCodeInternalDto {
        return QRCodeInternalDto(
            externalUserId = externalUserId,
            externalOptionId = externalOptionId,
            externalOrderId = externalOrderId,
            type = QRCodeType.OPTION,
            content = content,
            salt = salt,
            url = url
        )
    }

    private fun apply(qrcode: QRCodeInternalDto): QRCodeInternalDto {
        qrcode.applied = true
        qrcode.dateApplied = LocalDateTime.now()
        return qrcode
    }
}