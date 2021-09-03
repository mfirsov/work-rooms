package ru.mfirsov.workrooms.repository

import org.springframework.data.repository.CrudRepository
import ru.mfirsov.workrooms.repository.entity.RoomEntity

interface RoomRepository : CrudRepository<RoomEntity, Int> {
    fun findAllByOfficeId(officeId: Int): Set<RoomEntity>
}