package ru.softdarom.qrcheck.qrcode.handler.service

interface CryptoService {

    fun encrypt(content: String): String

    fun decrypt(content: String): String

}