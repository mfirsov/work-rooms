package ru.mfirsov.workrooms.controller

import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.mfirsov.workrooms.dto.WorkerDto
import ru.mfirsov.workrooms.service.WorkerService
import java.time.LocalDate

@RestController
@RequestMapping("/workers")
@Validated
class WorkerController(
    private val workerService: WorkerService
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/all")
    fun allWorkers(): Set<WorkerDto> {
        logger.info("Получен запрос на получение информации о всех сотрудниках")
        return workerService.getAllWorkers()
    }

    @GetMapping("/{workerId}")
    fun workerById(@PathVariable("workerId") workerId: Int): WorkerDto {
        logger.info("Получен запрос на получение информации о сотруднике $workerId")
        return workerService.getWorkerById(workerId)
    }

    @GetMapping("/active")
    fun activeWorkers(): Set<WorkerDto> {
        logger.info("Получен запрос на получение информации об активных сотрудниках")
        return workerService.getAllActiveWorkers()
    }

    @GetMapping("/fired")
    fun firedWorkers(): Set<WorkerDto> {
        logger.info("Получен запрос на получение информации об уволенных сотркдниках")
        return workerService.getAllFiredWorkers()
    }

    @GetMapping("/remote")
    fun remoteWorkers(): Set<WorkerDto> {
        logger.info("Получен запрос на получение информации об удаленных сотрудниках")
        return workerService.getAllRemoteWorkers()
    }

    @GetMapping("/{officeId}/{roomNumber}")
    fun allWorkersInOfficeAndRoom(@PathVariable("officeId") officeId: Int,
                                  @PathVariable("roomNumber") roomNumber: Int?): Set<WorkerDto> {
        logger.info("Получен запрос на получение информации о сотрудниках в офисе $officeId и комнате $roomNumber")
        return workerService.getWorkersFromOfficeAndRoom(officeId, roomNumber)
    }

    @PostMapping("/add")
    fun newWorker(@RequestBody workerDto: WorkerDto) {
        logger.info("Получен запрос на добавление нового сотрудника $workerDto")
        workerService.addNewWorker(workerDto)
    }

    @PutMapping("/{workerId}/update")
    fun updateWorker(@PathVariable("workerId") workerId: Int,
                     @RequestBody workerDto: WorkerDto) {
        logger.info("Получен запрос на обновление данных о сотруднике $workerId")
        workerService.updateWorker(workerId, workerDto)
    }

    @PutMapping("/{workerId}/move")
    fun moveWorker(@PathVariable("workerId") workerId: Int,
                   @RequestParam("officeId") officeId: Int?,
                   @RequestParam("roomNumber") roomNumber: Int?) {
        logger.info("Получен запрос нв перевод сотрудника в офис $officeId и кабинет $roomNumber")
        workerService.moveWorker(workerId, officeId, roomNumber)
    }

    @PutMapping("/{workerId}/fire")
    fun fireWorker(@PathVariable("workerId") workerId: Int,
                   @RequestParam("firingDate") firingDate: LocalDate) {
        logger.info("Получен запрос на увольнение сотрудника $workerId")
        workerService.fireWorker(workerId, firingDate)
    }
}