package ru.softdarom.qrcheck.qrcode.handler.model.dto.request

import com.sun.istack.NotNull
import org.springframework.validation.annotation.Validated
import ru.softdarom.qrcheck.qrcode.handler.model.dto.GeneratedQRCodeData
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

@Validated
data class GeneratedQRCodeRequest(
    @NotNull
    @Min(1)
    val externalUserId: Long,
    @NotEmpty
    val generatedQRCodeData: Set<GeneratedQRCodeData>
)
