package ru.softdarom.qrcheck.qrcode.handler.service.impl

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockMultipartFile
import org.springframework.stereotype.Service
import ru.softdarom.qrcheck.qrcode.handler.client.ContentHandlerExternalService
import ru.softdarom.qrcheck.qrcode.handler.service.ImageSaverService
import ru.softdarom.security.oauth2.config.property.ApiKeyProperties
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO


@Service
class ImageSaverServiceImpl(
    @Autowired private val contentHandlerExternalService: ContentHandlerExternalService,
    @Autowired private val apiKeyProperties: ApiKeyProperties
) : ImageSaverService {

    private val defaultSize = 500
    private val defaultApiVersion = "v1.0.0"
    private val encodeExtension = "jpeg"
    private val defaultQRCodeImageName = "default.qrcode"
    private val defaultMimeType = "image/jpeg"

    private val log = LoggerFactory.getLogger("SERVICE")

    override fun saveQRCodeContent(content: String): String {
        log.info("Save a content of a qrcode as image to the Content-Handler Service")
        val qrcode = generateQRCodeImage(content)
        val qrcodeAsBytes: ByteArray = getBytes(qrcode)
        val multipartFile = MockMultipartFile(defaultQRCodeImageName, defaultQRCodeImageName, defaultMimeType, qrcodeAsBytes)
        val response = contentHandlerExternalService.upload(defaultApiVersion, apiKeyProperties.token.outgoing, setOf(multipartFile))
        return response.images.map { it.url }.first()
    }

    private fun generateQRCodeImage(qrcodeText: String): BufferedImage {
        log.info("generate a qrcode via a text '{}' and a size '{}'", qrcodeText, defaultSize)
        val barcodeWriter = QRCodeWriter()
        val bitMatrix = barcodeWriter.encode(qrcodeText, BarcodeFormat.QR_CODE, defaultSize, defaultSize)
        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }

    private fun getBytes(qrcode: BufferedImage): ByteArray {
        val arrayOutputStream = ByteArrayOutputStream()
        arrayOutputStream.use {
            ImageIO.write(qrcode, encodeExtension, arrayOutputStream)
            arrayOutputStream.flush()
            val qrcodeAsBytes: ByteArray = arrayOutputStream.toByteArray()
            return qrcodeAsBytes
        }
    }
}