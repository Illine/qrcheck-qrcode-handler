package ru.softdarom.qrcheck.qrcode.handler.model.dto

import com.sun.istack.NotNull
import org.springframework.validation.annotation.Validated
import ru.softdarom.qrcheck.qrcode.handler.model.base.QRCodeType
import javax.validation.constraints.Min

@Validated
data class GeneratedQRCodeData(
    @NotNull
    @Min(1)
    val externalEventOrOptionId: Long,
    @NotNull
    @Min(1)
    val externalOrderId: Long,
    @NotNull
    val type: QRCodeType
)
