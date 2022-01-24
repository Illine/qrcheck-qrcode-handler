package ru.softdarom.qrcheck.qrcode.handler.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import ru.softdarom.qrcheck.orders.util.JsonHelper
import ru.softdarom.qrcheck.qrcode.handler.model.base.QRCodeType
import ru.softdarom.qrcheck.qrcode.handler.model.dto.internal.QRCodeInternalDto

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SavedQRCodeDto(
    var id: Long,
    var type: QRCodeType,
    var url: String
) {

    constructor(dto: QRCodeInternalDto) : this(dto.id!!, dto.type, dto.url)

    override fun toString(): String {
        return JsonHelper.asJson(this)
    }
}