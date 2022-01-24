package ru.softdarom.qrcheck.qrcode.handler.rest.handler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.softdarom.qrcheck.qrcode.handler.exception.CryptoException
import ru.softdarom.qrcheck.qrcode.handler.exception.NotFoundException
import ru.softdarom.security.oauth2.dto.response.ErrorResponse

@RestControllerAdvice
class QRCodeExceptionHandler {

    private val logger = LoggerFactory.getLogger("EXCEPTION-HANDLER")

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun forbiddenException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e.message, e)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse("Access Denied"))
    }

    @ExceptionHandler(value = [CryptoException::class])
    fun badRequestException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e.message, e)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(e.message))
    }

    @ExceptionHandler(value = [NotFoundException::class])
    fun notFoundException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e.message, e)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(e.message))
    }

    @ExceptionHandler(value = [Exception::class])
    fun exception(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unknown error: {}", e.message, e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse(e.message))
    }
}