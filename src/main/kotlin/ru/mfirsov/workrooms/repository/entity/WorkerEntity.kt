package ru.mfirsov.workrooms.repository.entity

import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "worker")
class WorkerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_id", unique = true)
    @JvmField
    var id: Int? = null,
    var firstName: String? = null,
    var middleName: String? = null,
    var lastName: String? = null,
    var birthDate: LocalDate? = null,
    var gender: String? = null,
    var hiringDate: LocalDate? = null,
    var firingDate: LocalDate? = null,
    @CreatedDate
    var createdDate: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedDate: LocalDateTime? = null,
    @Column(name = "office_id")
    var officeId: Int? = null,
    @Column(name = "room_number")
    var roomNumber: Int? = null
) : Persistable<Int> {
    override fun getId(): Int? {
        return this.id
    }

    override fun isNew(): Boolean {
        return null == this.createdDate
    }

    override fun toString(): String {
        return "WorkerEntity(id=$id, firstName=$firstName, middleName=$middleName, lastName=$lastName, birthDate=$birthDate, gender=$gender, hiringDate=$hiringDate, firingDate=$firingDate, officeId=$officeId, roomNumber=$roomNumber)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorkerEntity

        if (firstName != other.firstName) return false
        if (middleName != other.middleName) return false
        if (lastName != other.lastName) return false
        if (birthDate != other.birthDate) return false
        if (gender != other.gender) return false
        if (hiringDate != other.hiringDate) return false
        if (firingDate != other.firingDate) return false
        if (officeId != other.officeId) return false
        if (roomNumber != other.roomNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName?.hashCode() ?: 0
        result = 31 * result + (middleName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (birthDate?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (hiringDate?.hashCode() ?: 0)
        result = 31 * result + (firingDate?.hashCode() ?: 0)
        result = 31 * result + (officeId ?: 0)
        result = 31 * result + (roomNumber ?: 0)
        return result
    }
}
