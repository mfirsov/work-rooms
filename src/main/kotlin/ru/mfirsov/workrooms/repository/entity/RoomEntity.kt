package ru.mfirsov.workrooms.repository.entity

import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "room")
class RoomEntity(
    @Id
    @Column(name = "room_number", unique = true)
    @JvmField
    var roomNumber: Int,
    var workersLimit: Int,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "room_number", referencedColumnName = "room_number")
    var workers: MutableSet<WorkerEntity> = HashSet(),
    @CreatedDate
    var createdDate: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedDate: LocalDateTime? = null,
    @Column(name = "office_id")
    var officeId: Int? = null
) : Persistable<Int> {
    override fun getId(): Int {
        return this.roomNumber
    }

    override fun isNew(): Boolean {
        return null == this.createdDate
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoomEntity

        if (roomNumber != other.roomNumber) return false
        if (workersLimit != other.workersLimit) return false
        if (workers != other.workers) return false
        if (officeId != other.officeId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = roomNumber
        result = 31 * result + workersLimit
        result = 31 * result + workers.hashCode()
        result = 31 * result + (officeId ?: 0)
        return result
    }

    override fun toString(): String {
        return "RoomEntity(roomNumber=$roomNumber, workersLimit=$workersLimit, workers=$workers, officeId=$officeId)"
    }
}
