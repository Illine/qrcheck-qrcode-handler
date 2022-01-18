package ru.softdarom.qrcheck.qrcode.handler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QrcodeHandlerApplication

fun main(args: Array<String>) {
    runApplication<QrcodeHandlerApplication>(*args)
}
