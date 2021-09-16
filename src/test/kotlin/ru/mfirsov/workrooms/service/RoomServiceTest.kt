package ru.mfirsov.workrooms.service

import io.github.glytching.junit.extension.random.Random
import io.github.glytching.junit.extension.random.RandomBeansExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mapstruct.factory.Mappers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import ru.mfirsov.workrooms.dto.RoomDto
import ru.mfirsov.workrooms.mapper.RoomMapperImpl
import ru.mfirsov.workrooms.mapper.WorkerMapper
import ru.mfirsov.workrooms.repository.OfficeRepository
import ru.mfirsov.workrooms.repository.RoomRepository
import ru.mfirsov.workrooms.repository.entity.RoomEntity
import java.util.*

@ExtendWith(MockitoExtension::class, RandomBeansExtension::class)
internal class RoomServiceTest {
    @InjectMocks
    lateinit var roomService: RoomService
    @Mock
    lateinit var roomRepository: RoomRepository
    @Mock
    lateinit var officeRepository: OfficeRepository
    @Spy
    private val workerMapper = Mappers.getMapper(WorkerMapper::class.java)
    @Spy
    private val roomMapper = RoomMapperImpl(workerMapper)

    @Test
    fun getAllRooms(@Random(type = RoomEntity::class, size = 2) roomEntities: Set<RoomEntity>) {
        Mockito.`when`(roomRepository.findAll()).thenReturn(roomEntities)
        val rooms = roomService.getAllRooms()
        Assertions.assertNotNull(rooms)
        Assertions.assertEquals(2, rooms.size)
    }

    @Test
    fun getAllRoomsFromOffice(@Random(type = RoomEntity::class, size = 2) roomEntities: Set<RoomEntity>) {
        Mockito.`when`(roomRepository.findAllByOfficeId(Mockito.anyInt())).thenReturn(roomEntities)
        val rooms = roomService.getAllRoomsFromOffice(1)
        Assertions.assertNotNull(rooms)
        Assertions.assertEquals(2, rooms.size)
    }

    @Test
    fun getRoomByRoomNumber(@Random(type = RoomEntity::class) roomEntity: RoomEntity) {
        Mockito.`when`(roomRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(roomEntity))
        val roomDto = roomService.getRoomByRoomNumber(roomEntity.roomNumber)
        Assertions.assertNotNull(roomDto)
        Assertions.assertEquals(roomEntity.roomNumber, roomDto.roomNumber)
        Assertions.assertEquals(roomEntity.workersLimit, roomDto.workersLimit)
        Assertions.assertEquals(roomEntity.officeId, roomDto.officeId)
        Assertions.assertEquals(roomEntity.workers.size, roomDto.workers.size)
    }

    @Test
    fun addNewRoom(@Random(type = RoomDto::class) roomDto: RoomDto) {
        Mockito.`when`(officeRepository.existsById(Mockito.anyInt())).thenReturn(true)
        Assertions.assertDoesNotThrow { roomService.addNewRoom(roomDto) }
    }

    @Test
    fun updateWorkersLimit(@Random(type = RoomEntity::class) roomEntity: RoomEntity) {
        Mockito.`when`(roomRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(roomEntity))
        Assertions.assertDoesNotThrow { roomService.updateWorkersLimit(100, roomEntity.roomNumber)}
    }
}