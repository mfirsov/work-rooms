package ru.mfirsov.workrooms.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.mfirsov.workrooms.dto.WorkerDto
import ru.mfirsov.workrooms.repository.entity.WorkerEntity

@Mapper
interface WorkerMapper {

    fun workerEntityToDto(workerEntity: WorkerEntity): WorkerDto
    @Mappings(
        Mapping(target = "createdDate", ignore = true),
        Mapping(target = "updatedDate", ignore = true)
    )
    fun workerDtoToEntity(workerDto: WorkerDto): WorkerEntity
}