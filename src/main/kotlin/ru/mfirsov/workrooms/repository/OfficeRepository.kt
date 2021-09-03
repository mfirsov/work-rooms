package ru.mfirsov.workrooms.repository

import org.springframework.data.repository.CrudRepository
import ru.mfirsov.workrooms.repository.entity.OfficeEntity

interface OfficeRepository : CrudRepository<OfficeEntity, Int> {

}