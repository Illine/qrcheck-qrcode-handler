package ru.softdarom.qrcheck.qrcode.handler.model.dto.external.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import ru.softdarom.qrcheck.orders.util.JsonHelper
import ru.softdarom.qrcheck.qrcode.handler.model.dto.external.FileDto
import javax.validation.constraints.NotEmpty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FileResponse(
    @JsonProperty("files")
    val images: @NotEmpty MutableCollection<FileDto>
) {

    override fun toString(): String {
        return JsonHelper.asJson(this)
    }
}
