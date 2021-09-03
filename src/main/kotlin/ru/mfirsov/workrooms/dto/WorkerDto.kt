package ru.mfirsov.workrooms.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(value = ["id"], allowGetters = true)
data class WorkerDto(
    var id: Int? = null,
    @NotEmpty
    var firstName: String? = null,
    @NotEmpty
    var lastName: String? = null,
    @NotEmpty
    var middleName: String? = null,
    @NotNull
    var birthDate: LocalDate? = null,
    @NotEmpty
    var gender: String? = null,
    @NotNull
    var hiringDate: LocalDate? = null,
    var firingDate: LocalDate? = null,
    @NotNull
    var officeId: Int? = null,
    var roomNumber: Int? = null
)