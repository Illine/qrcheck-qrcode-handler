package ru.softdarom.qrcheck.qrcode.handler.service

interface ImageSaverService {

    fun saveQRCodeContent(content: String): String

}