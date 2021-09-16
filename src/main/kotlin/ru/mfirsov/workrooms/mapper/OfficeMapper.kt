package ru.mfirsov.workrooms.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.mfirsov.workrooms.dto.OfficeDto
import ru.mfirsov.workrooms.repository.entity.OfficeEntity

@Mapper(uses = [WorkerMapper::class, RoomMapper::class], injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface OfficeMapper {
    fun officeEntityToDto(office: OfficeEntity): OfficeDto
    @Mappings(
        Mapping(target = "createdDate", ignore = true),
        Mapping(target = "updatedDate", ignore = true)
    )
    fun officeDtoToEntity(officeDto: OfficeDto): OfficeEntity
}