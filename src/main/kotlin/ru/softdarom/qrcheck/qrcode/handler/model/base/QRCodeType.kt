package ru.softdarom.qrcheck.qrcode.handler.model.base

import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

enum class QRCodeType(@JsonValue val type: String) {

    EVENT("event"),
    OPTION("option");

    override fun toString(): String {
        return type
    }
}

fun typeOf(type: String): QRCodeType? {
    return EnumSet.allOf(QRCodeType::class.java)
        .stream()
        .filter { it.type == type }
        .findFirst()
        .orElseThrow()
}