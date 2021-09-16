package ru.mfirsov.workrooms.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(value = ["workers"], allowGetters = true)
class RoomDto(
    @NotNull
    var roomNumber: Int? = null,
    @NotNull
    var workersLimit: Int? = null,
    var workers: MutableSet<WorkerDto> = HashSet(),
    @NotNull
    var officeId: Int? = null
)
