package ru.softdarom.qrcheck.qrcode.handler.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import ru.softdarom.qrcheck.qrcode.handler.config.FeignConfig
import ru.softdarom.qrcheck.qrcode.handler.model.dto.external.response.FileResponse

@FeignClient(name = "content-handler", url = "\${outbound.feign.content-handler.host}", configuration = [FeignConfig::class])
interface ContentHandlerExternalService {

    @PostMapping(value = ["/files/"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(
        @RequestHeader("X-Application-Version") version: String,
        @RequestHeader("X-ApiKey-Authorization") apiKey: String,
        @RequestPart("files") files: Collection<MultipartFile>
    ): FileResponse

}