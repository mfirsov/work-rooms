package ru.mfirsov.workrooms.repository.entity

import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "office")
class OfficeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JvmField
    var id: Int? = null,
    var address: String? = null,
    var city: String? = null,
    var country: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    var workers: MutableSet<WorkerEntity> = HashSet(),
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    var rooms: MutableSet<RoomEntity> = HashSet(),
    @CreatedDate
    var createdDate: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedDate: LocalDateTime? = null
) : Persistable<Int> {

    override fun isNew(): Boolean {
        return null == this.createdDate
    }

    override fun getId(): Int? {
        return this.id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OfficeEntity

        if (address != other.address) return false
        if (city != other.city) return false
        if (country != other.country) return false
        if (workers != other.workers) return false
        if (rooms != other.rooms) return false

        return true
    }

    override fun hashCode(): Int {
        var result = address?.hashCode() ?: 0
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (country?.hashCode() ?: 0)
        result = 31 * result + workers.hashCode()
        result = 31 * result + rooms.hashCode()
        return result
    }

    override fun toString(): String {
        return "OfficeEntity(id=$id, address=$address, city=$city, country=$country, workers=$workers, rooms=$rooms)"
    }

}
