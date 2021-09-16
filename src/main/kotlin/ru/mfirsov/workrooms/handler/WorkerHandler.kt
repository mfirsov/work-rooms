package ru.mfirsov.workrooms.handler

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.mfirsov.workrooms.dto.WorkerDto
import ru.mfirsov.workrooms.service.WorkerService
import java.time.LocalDate

@Service
class WorkerHandler(
    private val workerService: WorkerService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getAllWorkers(serverRequest: ServerRequest): ServerResponse =
        ok().body(workerService.getAllWorkers())

    fun getAllRemoteWorkers(serverRequest: ServerRequest): ServerResponse =
        ok().body(workerService.getAllRemoteWorkers())

    fun getAllActiveWorkers(serverRequest: ServerRequest): ServerResponse =
        ok().body(workerService.getAllActiveWorkers())

    fun getAllFiredWorkers(serverRequest: ServerRequest): ServerResponse =
        ok().body(workerService.getAllFiredWorkers())

    fun getWorkerById(serverRequest: ServerRequest): ServerResponse {
        val workerId = serverRequest.pathVariable("workerId").toInt()
        return ok().body(workerService.getWorkerById(workerId))
    }

    fun addWorker(serverRequest: ServerRequest): ServerResponse {
        val workerDto = serverRequest.body(WorkerDto::class.java)
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(workerService.addNewWorker(workerDto))
    }

    fun updateWorker(serverRequest: ServerRequest): ServerResponse {
        val workerDto = serverRequest.body(WorkerDto::class.java)
        val workerId = serverRequest.pathVariable("workerId").toInt()
        return ok().contentType(MediaType.APPLICATION_JSON).body(workerService.updateWorker(workerId, workerDto))
    }

    fun moveWorker(serverRequest: ServerRequest): ServerResponse {
        val workerId = serverRequest.pathVariable("workerId").toInt()
        val officeId = serverRequest.param("officeId").map { it.toInt() }.orElseThrow()
        val roomNumber = serverRequest.param("roomNumber").map { it.toInt() }.orElseThrow()
        workerService.moveWorker(workerId, officeId, roomNumber)
        return ok().build()
    }

    fun getWorkersFromOfficeAndRoom(serverRequest: ServerRequest): ServerResponse {
        val officeId = serverRequest.pathVariable("officeId").toInt()
        val roomNumber = serverRequest.pathVariable("roomNumber").toInt()
        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(workerService.getWorkersFromOfficeAndRoom(officeId, roomNumber))
    }

    fun firingDate(serverRequest: ServerRequest): ServerResponse {
        val workerId = serverRequest.pathVariable("workerId").toInt()
        val firingDate = serverRequest.param("firingDate").map { LocalDate.parse(it) }.orElseThrow()
        workerService.fireWorker(workerId, firingDate)
        return ok().build()
    }
}