package ru.mfirsov.workrooms.repository

import org.springframework.data.repository.CrudRepository
import ru.mfirsov.workrooms.repository.entity.WorkerEntity

interface WorkerRepository : CrudRepository<WorkerEntity, Int> {
    fun findAllByFiringDateNotNull(): Set<WorkerEntity>
    fun findAllByRoomNumberIsNull(): Set<WorkerEntity>
    fun findAllByOfficeIdAndRoomNumber(officeId: Int, roomNumber: Int?): Set<WorkerEntity>
}