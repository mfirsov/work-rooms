package ru.mfirsov.workrooms.controller

import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.mfirsov.workrooms.dto.RoomDto
import ru.mfirsov.workrooms.service.RoomService

@RestController
@RequestMapping("/rooms")
@Validated
class RoomController(
    private val roomService: RoomService
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/all")
    fun allRooms(): Set<RoomDto> {
        logger.info("Получен запрос на получение всех кабинетов")
        return roomService.getAllRooms()
    }

    @GetMapping("/all/{officeId}")
    fun allRoomsInOffice(@PathVariable("officeId") officeId: Int): Set<RoomDto> {
        logger.info("Получение всех кабинетов из офиса $officeId")
        return roomService.getAllRoomsFromOffice(officeId)
    }

    @GetMapping("/{roomNumber}")
    fun roomById(@PathVariable("roomNumber") roomNumber: Int): RoomDto {
        logger.info("Получен запрос на получение кабинета с номером $roomNumber")
        return roomService.getRoomByRoomNumber(roomNumber)
    }

    @PostMapping("/add")
    fun newRoom(@RequestBody roomDto: RoomDto) {
        logger.info("Получен запрос на добавление новой комнаты")
        roomService.addNewRoom(roomDto)
    }

    @PutMapping("/{roomNumber}/{workersLimit}")
    fun updateWorkerLimits(@PathVariable("roomNumber") roomNumber: Int,
                           @PathVariable("workersLimit") workerLimit: Int) {
        logger.info("Получен запрос на изменение лимита сотрудников в комнате")
        roomService.updateWorkersLimit(workerLimit, roomNumber)
    }
}