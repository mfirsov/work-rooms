package ru.mfirsov.workrooms.handler

import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ru.mfirsov.workrooms.dto.OfficeDto
import ru.mfirsov.workrooms.service.OfficeService

@Service
class OfficeHandler(
    private val officeService: OfficeService
) {
   fun getAllOffices(serverRequest: ServerRequest): ServerResponse {
       return ServerResponse.ok().body(officeService.getAllOffices())
   }

    fun getOfficeById(serverRequest: ServerRequest): ServerResponse {
        val officeId = serverRequest.pathVariable("officeId").toInt()
        return ServerResponse.ok().body(officeService.getOfficeById(officeId)!!)
    }

    fun addOffice(serverRequest: ServerRequest): ServerResponse {
        val officeDto = serverRequest.body(OfficeDto::class.java)
        officeService.addNewOffice(officeDto)
        return ServerResponse.ok().build()
    }
}