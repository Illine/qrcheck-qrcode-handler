package ru.softdarom.qrcheck.qrcode.handler.dao.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import ru.softdarom.qrcheck.orders.util.JsonHelper
import ru.softdarom.qrcheck.qrcode.handler.model.base.QRCodeType
import ru.softdarom.qrcheck.qrcode.handler.model.dto.internal.QRCodeInternalDto
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(
    name = "qrcodes",
    indexes = [Index(name = "qrcodes_external_user_id_content_index", columnList = "external_user_id,content", unique = true)]
)
class QRCodeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qrcodeSeqGenerator")
    @SequenceGenerator(
        name = "qrcodeSeqGenerator",
        sequenceName = "qrcode_seq",
        allocationSize = 1
    )
    val id: Long? = null,

    @Column(name = "external_user_id", nullable = false, updatable = false)
    val externalUserId: Long,

    @Column(name = "external_event_id", updatable = false)
    val externalEventId: Long?,

    @Column(name = "external_option_id", updatable = false)
    val externalOptionId: Long?,

    @Column(name = "external_order_id", nullable = false, updatable = false)
    val externalOrderId: Long,

    @Column(name = "url", nullable = false, updatable = false)
    val url: String,

    @Column(name = "type", nullable = false, updatable = false)
    val type: QRCodeType,

    @Column(name = "content", nullable = false, updatable = false)
    val content: String,

    @Column(name = "salt", nullable = false, updatable = false)
    val salt: String,

    @Column(name = "applied", nullable = false)
    var applied: Boolean = false,

    @Column(name = "date_applied")
    var dateApplied: LocalDateTime? = null,

    @Column(name = "created", updatable = false)
    @JsonIgnore
    var created: LocalDateTime?,

    @Column(name = "updated", nullable = false)
    @JsonIgnore
    var modified: LocalDateTime?
) {

    constructor(dto: QRCodeInternalDto) : this(
        id = dto.id,
        externalUserId = dto.externalUserId,
        externalEventId = dto.externalEventId,
        externalOptionId = dto.externalOptionId,
        externalOrderId = dto.externalOrderId,
        url = dto.url,
        type = dto.type,
        content = dto.content,
        salt = dto.salt,
        applied = dto.applied,
        dateApplied = dto.dateApplied,
        created = dto.created,
        modified = dto.modified
    )

    @PrePersist
    protected fun onCreate() {
        val now = LocalDateTime.now()
        created = now
        modified = now
    }

    @PreUpdate
    protected fun onUpdate() {
        modified = LocalDateTime.now()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as QRCodeEntity

        if (id != other.id) return false
        if (externalUserId != other.externalUserId) return false
        if (externalEventId != other.externalEventId) return false
        if (externalOptionId != other.externalOptionId) return false
        if (externalOrderId != other.externalOrderId) return false
        if (type != other.type) return false
        if (salt != other.salt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + externalUserId.hashCode()
        result = 31 * result + (externalEventId?.hashCode() ?: 0)
        result = 31 * result + (externalOptionId?.hashCode() ?: 0)
        result = 31 * result + externalOrderId.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + salt.hashCode()
        return result
    }

    override fun toString(): String {
        return JsonHelper.asJson(this)
    }
}