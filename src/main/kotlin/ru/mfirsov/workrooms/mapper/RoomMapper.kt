package ru.mfirsov.workrooms.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import ru.mfirsov.workrooms.dto.RoomDto
import ru.mfirsov.workrooms.repository.entity.RoomEntity

@Mapper(uses = [WorkerMapper::class], injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface RoomMapper {
    fun roomEntityToDto(room: RoomEntity): RoomDto
    @Mappings(
        Mapping(target = "createdDate", ignore = true),
        Mapping(target = "updatedDate", ignore = true)
    )
    fun roomDtoToEntity(roomDto: RoomDto): RoomEntity
}