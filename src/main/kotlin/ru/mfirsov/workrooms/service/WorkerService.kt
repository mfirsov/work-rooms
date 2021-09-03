package ru.mfirsov.workrooms.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.mfirsov.workrooms.dto.WorkerDto
import ru.mfirsov.workrooms.mapper.WorkerMapper
import ru.mfirsov.workrooms.repository.RoomRepository
import ru.mfirsov.workrooms.repository.WorkerRepository
import ru.mfirsov.workrooms.repository.entity.WorkerEntity
import java.util.*
import javax.validation.Valid

@Service
@Validated
class WorkerService(
    private val workerRepository: WorkerRepository,
    private val workerMapper: WorkerMapper,
    private val officeRepository: WorkerRepository,
    private val roomRepository: RoomRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getAllWorkers(): Set<WorkerDto> {
        logger.info("Return all available workers")
        return workerRepository.findAll().map { workerMapper.workerEntityToDto(it) }.toSet()
    }

    fun getAllActiveWorkers(): Set<WorkerDto> =
        convertEntityToDto(workerRepository.findAllByHiringDateNotNullAndFiringDateIsNull())

    fun getAllFiredWorkers(): Set<WorkerDto> =
        convertEntityToDto(workerRepository.findAllByFiringDateNotNull())

    fun getWorkerById(id: Int): WorkerDto =
        workerRepository.findById(id)
            .map { workerMapper.workerEntityToDto(it) }
            .orElseThrow()

    fun getAllRemoteWorkers(): Set<WorkerDto> =
        convertEntityToDto(workerRepository.findAllByRoomNumberIsNull())

    fun addNewWorker(@Valid workerDto: WorkerDto) {
        workerDto.officeId?.let {
            if (!officeRepository.existsById(it)) {
                throw NoSuchElementException("Невозможно добавить нового работника. Офис с officeId: $it не существует.")
            }
        }
        if (workerDto.firingDate?.isBefore(workerDto.hiringDate) == true) {
            throw IllegalArgumentException("Дата увольнения не может быть раньше даты найма")
        }
        checkWorkersLimit(workerDto)
        val workerEntity = workerMapper.workerDtoToEntity(workerDto)
        workerRepository.save(workerEntity)
    }

    fun updateWorker(workerId: Int, @Valid workerDto: WorkerDto) {
        workerRepository.findById(workerId).ifPresentOrElse(
            {
                if (it.roomNumber != workerDto.roomNumber) {
                    checkWorkersLimit(workerDto)
                }
                if (workerDto.firingDate?.isBefore(workerDto.hiringDate) == true) {
                    throw IllegalArgumentException("Дата увольнения не может быть раньше даты найма")
                }
                it.firstName = workerDto.firstName
                it.lastName = workerDto.lastName
                it.middleName = workerDto.middleName
                it.birthDate = workerDto.birthDate
                it.hiringDate = workerDto.hiringDate
                it.firingDate = workerDto.firingDate
                it.gender = workerDto.gender
                it.officeId = workerDto.officeId
                it.roomNumber = workerDto.roomNumber

                workerRepository.save(it)
            },
            { throw NoSuchElementException("Невозможно обновить информацию по работнику. Работника с id: $workerId не существует") }
        )
    }

    fun moveWorker(workerId: Int, officeId: Int?, roomNumber: Int?) {
        roomNumber?.let {
            val roomEntity = roomRepository.findById(it).orElseThrow()
            if (roomEntity.workersLimit == roomEntity.workers.size) {
                throw IllegalArgumentException("Невозможно переместить сотрудника в кабинет $it. Достигнут лимит сотрудников ${roomEntity.workersLimit}")
            }
        }
        val workerEntity = workerRepository.findById(workerId).orElseThrow()
        officeId.let { workerEntity.officeId = officeId }
        workerEntity.roomNumber = roomNumber
    }

    private fun checkWorkersLimit(workerDto: WorkerDto) {
        workerDto.roomNumber?.let {
            val roomEntity = roomRepository.findById(it).orElseThrow()
            val workersLimit = roomEntity.workersLimit
            if (workersLimit - roomEntity.workers.size <= 0) {
                throw RuntimeException("Невозможно добавить нового работника в комнату $it. Достигунт лимит сотрудников $workersLimit")
            }
        }
    }

    private fun convertEntityToDto(workerEntities: Set<WorkerEntity>): Set<WorkerDto> {
        return if (workerEntities.isEmpty()) {
            Collections.emptySet()
        } else {
            workerEntities.map { workerMapper.workerEntityToDto(it) }.toSet()
        }
    }
}