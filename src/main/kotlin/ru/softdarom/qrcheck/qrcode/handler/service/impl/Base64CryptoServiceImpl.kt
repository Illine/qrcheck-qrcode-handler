package ru.softdarom.qrcheck.qrcode.handler.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.softdarom.qrcheck.qrcode.handler.exception.CryptoException
import ru.softdarom.qrcheck.qrcode.handler.service.CryptoService
import java.util.*

@Service
class Base64CryptoServiceImpl : CryptoService {

    private val log = LoggerFactory.getLogger("SERVICE")

    override fun encrypt(content: String): String {
        log.info("Encrypting a string as base64")
        log.debug("The string is {}", content)
        return Base64.getEncoder().withoutPadding().encodeToString(content.encodeToByteArray())
    }

    override fun decrypt(content: String): String {
        log.info("Decrypting a string: {}", content)
        try {
            return String(Base64.getDecoder().decode(content.encodeToByteArray()))
        } catch (e: Exception) {
            throw CryptoException("Error occurs during decrypting: '$content'", e)
        }
    }
}