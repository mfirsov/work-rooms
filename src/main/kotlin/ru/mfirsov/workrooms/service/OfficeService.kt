package ru.mfirsov.workrooms.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.mfirsov.workrooms.dto.OfficeDto
import ru.mfirsov.workrooms.mapper.OfficeMapper
import ru.mfirsov.workrooms.repository.OfficeRepository

@Service
class OfficeService(
    private val officeRepository: OfficeRepository,
    private val officeMapper: OfficeMapper
) {
    fun getAllOffices(): Set<OfficeDto> =
        officeRepository.findAll().map { officeMapper.officeEntityToDto(it) }.toSet()

    fun getOfficeById(officeId: Int): OfficeDto? =
        officeRepository.findByIdOrNull(officeId)?.let { officeMapper.officeEntityToDto(it) }

    fun addNewOffice(officeDto: OfficeDto) {
        val officeEntity = officeMapper.officeDtoToEntity(officeDto)
        officeRepository.save(officeEntity)
    }
}