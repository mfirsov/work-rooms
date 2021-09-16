package ru.mfirsov.workrooms.service

import io.github.glytching.junit.extension.random.Random
import io.github.glytching.junit.extension.random.RandomBeansExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import ru.mfirsov.workrooms.dto.OfficeDto
import ru.mfirsov.workrooms.mapper.OfficeMapperImpl
import ru.mfirsov.workrooms.mapper.RoomMapperImpl
import ru.mfirsov.workrooms.mapper.WorkerMapperImpl
import ru.mfirsov.workrooms.repository.OfficeRepository
import ru.mfirsov.workrooms.repository.entity.OfficeEntity
import java.util.*

@ExtendWith(MockitoExtension::class, RandomBeansExtension::class)
internal class OfficeServiceTest {

    @InjectMocks
    lateinit var officeService: OfficeService
    @Mock
    lateinit var officeRepository: OfficeRepository
    private val workerMapper = WorkerMapperImpl()
    private val roomMapper = RoomMapperImpl(workerMapper)
    @Spy
    private val officeMapper = OfficeMapperImpl(workerMapper, roomMapper)

    @Test
    fun getAllOffices(@Random(type = OfficeEntity::class, size = 2) officeEntities: Set<OfficeEntity>) {
        `when`(officeRepository.findAll()).thenReturn(officeEntities)
        val offices = officeService.getAllOffices()
        assertEquals(2, offices.size)
    }

    @Test
    fun getOfficeById(@Random(type = OfficeEntity::class) officeEntity: OfficeEntity) {
        `when`(officeRepository.findById(Mockito.any())).thenReturn(Optional.of(officeEntity))
        val officeDto = officeService.getOfficeById(officeEntity.id!!)
        assertNotNull(officeDto)
        assertEquals(officeEntity.id, officeDto!!.id)
        assertEquals(officeEntity.address, officeDto.address)
        assertEquals(officeEntity.city, officeDto.city)
        assertEquals(officeEntity.country, officeDto.country)
        assertEquals(officeEntity.rooms.size, officeDto.rooms.size)
        assertEquals(officeEntity.workers.size, officeDto.workers.size)
    }

    @Test
    fun addNewOffice(@Random(type = OfficeDto::class) officeDto: OfficeDto) {
        Assertions.assertDoesNotThrow { officeService.addNewOffice(officeDto) }
    }
}