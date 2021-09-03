package ru.mfirsov.workrooms.bootstrap

import org.springframework.boot.CommandLineRunner
import ru.mfirsov.workrooms.repository.OfficeRepository
import ru.mfirsov.workrooms.repository.entity.OfficeEntity
import ru.mfirsov.workrooms.repository.entity.RoomEntity
import ru.mfirsov.workrooms.repository.entity.WorkerEntity
import java.time.LocalDate

//@Component
class InitDbData(private val officeRepository: OfficeRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val workerWithoutRoom = WorkerEntity()
        workerWithoutRoom.firstName = "FirstName1"
        workerWithoutRoom.lastName = "LastName2"
        workerWithoutRoom.middleName = "MiddleName3"
        workerWithoutRoom.birthDate = LocalDate.of(1992, 8, 23)
        workerWithoutRoom.hiringDate = LocalDate.of(2021, 2, 28)
        workerWithoutRoom.gender = "M"

        val singleWorkerInRoom = WorkerEntity()
        singleWorkerInRoom.gender = "M"
        singleWorkerInRoom.hiringDate = LocalDate.of(2019, 3, 3)
        singleWorkerInRoom.birthDate = LocalDate.of(1994, 8, 23)
        singleWorkerInRoom.firstName = "fjgdfghdfg"
        singleWorkerInRoom.lastName = "fghdfgjbhfggdhghgjgjbfbd"
        singleWorkerInRoom.middleName = "78ghq3uyqgiavfuy"

        val workerMale = WorkerEntity()
        workerMale.firstName = "MaleFirstName1"
        workerMale.lastName = "MaleLastName2"
        workerMale.middleName = "maleMiddleName3"
        workerMale.hiringDate = LocalDate.of(2021, 3, 28)
        workerMale.birthDate = LocalDate.of(1992, 7, 12)
        workerMale.gender = "M"

        val workerFemale = WorkerEntity()
        workerFemale.firstName = "FemaleFirstName1"
        workerFemale.lastName = "FemaleLastName2"
        workerFemale.middleName = "FemaleMiddleName3"
        workerFemale.hiringDate = LocalDate.of(2020, 6, 28)
        workerFemale.birthDate = LocalDate.of(1991, 7, 12)
        workerFemale.gender = "F"

        val roomWithTwoWorkers = RoomEntity(701, 2)
        roomWithTwoWorkers.workers.add(workerMale)
        roomWithTwoWorkers.workers.add(workerFemale)

        val roomWithOneWorker = RoomEntity(702, 2)
        roomWithOneWorker.workers.add(singleWorkerInRoom)

        val emptyRoom = RoomEntity(703, 10)

        val office = OfficeEntity()
        office.address = "dfljghdiugkugkdgv"
        office.city = "Saratov"
        office.country = "RF"
        office.workers.add(workerMale)
        office.workers.add(workerFemale)
        office.workers.add(singleWorkerInRoom)
        office.workers.add(workerWithoutRoom)
        office.rooms.add(roomWithOneWorker)
        office.rooms.add(roomWithTwoWorkers)
        office.rooms.add(emptyRoom)

        officeRepository.save(office)
    }
}