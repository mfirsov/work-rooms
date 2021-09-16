package ru.mfirsov.workrooms.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router
import ru.mfirsov.workrooms.handler.OfficeHandler
import ru.mfirsov.workrooms.handler.RoomHandler
import ru.mfirsov.workrooms.handler.WorkerHandler

@Configuration
class RouterConfiguration(
    private val workerHandler: WorkerHandler,
    private val officeHandler: OfficeHandler,
    private val roomHandler: RoomHandler
) {

    @Bean
    fun routes() = router {
        "/handler".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                "/workers".nest {
                    GET("/all", workerHandler::getAllWorkers)
                    GET("/{workerId}", workerHandler::getWorkerById)
                    GET("/active", workerHandler::getAllActiveWorkers)
                    GET("/fired", workerHandler::getAllFiredWorkers)
                    GET("/remote", workerHandler::getAllRemoteWorkers)
                    GET("/{officeId}/{roomNumber}", workerHandler::getWorkersFromOfficeAndRoom)
                    POST("/add", workerHandler::addWorker)
                    PUT("/{workerId}/update", workerHandler::updateWorker)
                    PUT("/{workerId}/move", workerHandler::moveWorker)
                    PUT("/{workerId}/fire", workerHandler::firingDate)
                }
                "/offices".nest {
                    GET("/all", officeHandler::getAllOffices)
                    GET("/{officeId}", officeHandler::getOfficeById)
                    POST("/add", officeHandler::addOffice)
                }
                "/rooms".nest {
                    GET("/all", roomHandler::getAllRooms)
                    GET("/{roomNumber}", roomHandler::getRoomById)
                    GET("/all/{officeId}", roomHandler::getAllRoomsFromOffice)
                    POST("/add", roomHandler::newRoom)
                    PUT("/{roomNumber}/{workersLimit}", roomHandler::updateLimit)
                }
            }
        }

    }

}