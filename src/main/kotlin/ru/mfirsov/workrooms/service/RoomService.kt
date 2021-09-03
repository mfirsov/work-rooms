package ru.mfirsov.workrooms.service

import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.mfirsov.workrooms.dto.RoomDto
import ru.mfirsov.workrooms.mapper.RoomMapper
import ru.mfirsov.workrooms.repository.OfficeRepository
import ru.mfirsov.workrooms.repository.RoomRepository

@Service
@Validated
class RoomService(
    private val roomRepository: RoomRepository,
    private val roomMapper: RoomMapper,
    private val officeRepository: OfficeRepository
) {
    fun getAllRooms(): Set<RoomDto> =
        roomRepository.findAll().map { roomMapper.roomEntityToDto(it) }.toSet()

    fun getAllRoomsFromOffice(officeId: Int): Set<RoomDto> =
        roomRepository.findAllByOfficeId(officeId).map { roomMapper.roomEntityToDto(it) }.toSet()

    fun getRoomByRoomNumber(roomNumber: Int): RoomDto =
        roomRepository.findById(roomNumber).map { roomMapper.roomEntityToDto(it) }.orElseThrow()

    fun addNewRoom(roomDto: RoomDto) {
        roomDto.officeId.let {
            if (it == null || !officeRepository.existsById(it)) {
                throw IllegalArgumentException("Невозможно добавить кабинет. Офиса с officeId: $it не существует")
            }
        }
        roomMapper.roomDtoToEntity(roomDto).also { roomRepository.save(it) }
    }

    fun updateWorkersLimit(newLimit: Int, roomNumber: Int) {
        if (newLimit <= 0) throw IllegalArgumentException("Новый лимит работников должен быть больше 0. Текущее значение $newLimit.")
        val roomEntity = roomRepository.findById(roomNumber).orElseThrow()
        if (roomEntity.workers.size < newLimit)
            throw IllegalArgumentException("Новый лимит работников не может быть ниже текущего количества работников в кабинете. Удалите или переместите сотрудника.")
        roomEntity.workersLimit = newLimit
        roomRepository.save(roomEntity)
    }
}