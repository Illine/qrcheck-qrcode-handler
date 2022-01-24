package ru.softdarom.qrcheck.qrcode.handler.model.dto.external

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import ru.softdarom.qrcheck.orders.util.JsonHelper

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FileDto(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("url")
    val url: String
) {

    override fun toString(): String {
        return JsonHelper.asJson(this)
    }
}
