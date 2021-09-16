package ru.mfirsov.workrooms.controller

import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.mfirsov.workrooms.dto.OfficeDto
import ru.mfirsov.workrooms.service.OfficeService

@RestController
@RequestMapping("/offices")
@Validated
class OfficeController(
    private val officeService: OfficeService
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/all")
    fun allOffices(): Set<OfficeDto> {
        logger.info("Получен запрос на все доступные офисы")
        return officeService.getAllOffices()
    }

    @GetMapping("/{officeId}")
    fun officeById(@PathVariable("officeId") officeId: Int): OfficeDto? {
        logger.info("Получен запрос на получение информации по офису $officeId")
        return officeService.getOfficeById(officeId)
    }

    @PostMapping("/add")
    fun newOffice(@RequestBody officeDto: OfficeDto) {
        logger.info("Получен запрос на добавление нового офиса $officeDto")
        return officeService.addNewOffice(officeDto)
    }

}