package ru.mfirsov.workrooms.handler

import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ru.mfirsov.workrooms.dto.RoomDto
import ru.mfirsov.workrooms.service.RoomService

@Service
class RoomHandler(
    private val roomService: RoomService
) {
    fun getAllRooms(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok().body(roomService.getAllRooms())
    }

    fun getAllRoomsFromOffice(serverRequest: ServerRequest): ServerResponse {
        val officeId = serverRequest.pathVariable("officeId").toInt()
        return ServerResponse.ok().body(roomService.getAllRoomsFromOffice(officeId))
    }

    fun getRoomById(serverRequest: ServerRequest): ServerResponse {
        val roomNumber = serverRequest.pathVariable("roomNumber").toInt()
        return ServerResponse.ok().body(roomService.getRoomByRoomNumber(roomNumber))
    }

    fun updateLimit(serverRequest: ServerRequest): ServerResponse {
        val roomNumber = serverRequest.pathVariable("roomNumber").toInt()
        val workersLimit = serverRequest.pathVariable("workersLimit").toInt()
        roomService.updateWorkersLimit(workersLimit,roomNumber)
        return ServerResponse.ok().build()
    }

    fun newRoom(serverRequest: ServerRequest): ServerResponse {
        val roomDto = serverRequest.body(RoomDto::class.java)
        roomService.addNewRoom(roomDto)
        return ServerResponse.ok().build()
    }
}