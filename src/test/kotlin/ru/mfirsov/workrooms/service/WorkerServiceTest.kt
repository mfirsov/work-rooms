package ru.mfirsov.workrooms.service

import io.github.glytching.junit.extension.random.Random
import io.github.glytching.junit.extension.random.RandomBeansExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import ru.mfirsov.workrooms.dto.WorkerDto
import ru.mfirsov.workrooms.mapper.WorkerMapperImpl
import ru.mfirsov.workrooms.repository.OfficeRepository
import ru.mfirsov.workrooms.repository.RoomRepository
import ru.mfirsov.workrooms.repository.WorkerRepository
import ru.mfirsov.workrooms.repository.entity.RoomEntity
import ru.mfirsov.workrooms.repository.entity.WorkerEntity
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class, RandomBeansExtension::class)
class WorkerServiceTest {
    @InjectMocks
    lateinit var workerService: WorkerService

    @Mock
    lateinit var workerRepository: WorkerRepository

    @Mock
    lateinit var roomRepository: RoomRepository

    @Mock
    lateinit var officeRepository: OfficeRepository

    @Spy
    private val workerMapper = WorkerMapperImpl()

    @Test
    fun addNewWorkerTest(
        @Random(type = WorkerDto::class, excludes = ["firingDate"]) workerDto: WorkerDto,
        @Random(type = RoomEntity::class) roomEntity: RoomEntity
    ) {
        roomEntity.workersLimit = 100
        Mockito.`when`(officeRepository.existsById(Mockito.anyInt())).thenReturn(true)
        Mockito.`when`(roomRepository.existsById(Mockito.anyInt())).thenReturn(true)
        Mockito.`when`(roomRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(roomEntity))
        Assertions.assertDoesNotThrow { workerService.addNewWorker(workerDto) }
    }

    @Test
    fun updateWorkerTest(
        @Random(type = WorkerDto::class, excludes = ["firingDate"]) workerDto: WorkerDto,
        @Random(type = WorkerEntity::class, excludes = ["firingDate"]) workerEntity: WorkerEntity,
        @Random(type = RoomEntity::class) roomEntity: RoomEntity
    ) {
        roomEntity.workersLimit = 100
        Mockito.`when`(workerRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(workerEntity))
        Mockito.`when`(roomRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(roomEntity))
        Assertions.assertDoesNotThrow { workerService.updateWorker(1, workerDto) }
    }

    @Test
    fun moveWorkerTest(
        @Random(type = WorkerEntity::class, excludes = ["firingDate"]) workerEntity: WorkerEntity,
        @Random(type = RoomEntity::class) roomEntity: RoomEntity
    ) {
        roomEntity.workersLimit = 100
        Mockito.`when`(roomRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(roomEntity))
        Mockito.`when`(workerRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(workerEntity))
        Assertions.assertDoesNotThrow { workerService.moveWorker(1, 1, 1) }
    }

    @Test
    fun fireWorker(@Random(type = WorkerEntity::class) workerEntity: WorkerEntity) {
        workerEntity.hiringDate = LocalDate.of(2021, 9, 15)
        Mockito.`when`(workerRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(workerEntity))
        Assertions.assertDoesNotThrow { workerService.fireWorker(1, LocalDate.now()) }
    }

}