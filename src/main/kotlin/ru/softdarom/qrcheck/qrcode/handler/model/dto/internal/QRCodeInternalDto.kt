package ru.softdarom.qrcheck.qrcode.handler.model.dto.internal

import com.fasterxml.jackson.annotation.JsonInclude
import ru.softdarom.qrcheck.orders.util.JsonHelper
import ru.softdarom.qrcheck.qrcode.handler.dao.entity.QRCodeEntity
import ru.softdarom.qrcheck.qrcode.handler.model.base.QRCodeType
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class QRCodeInternalDto(
    val id: Long? = null,
    val externalUserId: Long,
    val externalEventId: Long? = null,
    val externalOptionId: Long? = null,
    val externalOrderId: Long,
    val url: String,
    val type: QRCodeType,
    val content: String,
    val salt: String,
    var applied: Boolean = false,
    var dateApplied: LocalDateTime? = null,
    val created: LocalDateTime? = null,
    val modified: LocalDateTime? = null
) {

    constructor(entity: QRCodeEntity) : this(
        id = entity.id,
        externalUserId = entity.externalUserId,
        externalEventId = entity.externalEventId,
        externalOptionId = entity.externalOptionId,
        externalOrderId = entity.externalOrderId,
        url = entity.url,
        type = entity.type,
        content = entity.content,
        salt = entity.salt,
        applied = entity.applied,
        dateApplied = entity.dateApplied,
        created = entity.created,
        modified = entity.modified
    )

    override fun toString(): String {
        return JsonHelper.asJson(this)
    }
}