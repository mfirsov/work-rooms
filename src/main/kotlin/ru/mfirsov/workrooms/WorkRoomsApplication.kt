package ru.mfirsov.workrooms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkRoomsApplication

fun main(args: Array<String>) {
	runApplication<WorkRoomsApplication>(*args)
}
