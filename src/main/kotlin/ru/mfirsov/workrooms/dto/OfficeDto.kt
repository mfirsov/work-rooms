package ru.mfirsov.workrooms.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotEmpty

@JsonIgnoreProperties(value = ["id","workers","rooms"], allowGetters = true)
data class OfficeDto(
    var id: Int? = null,
    @NotEmpty
    var address: String? = null,
    @NotEmpty
    var city: String? = null,
    @NotEmpty
    var country: String? = null,
    var workers: MutableSet<WorkerDto> = HashSet(),
    var rooms: MutableSet<RoomDto> = HashSet()
)
