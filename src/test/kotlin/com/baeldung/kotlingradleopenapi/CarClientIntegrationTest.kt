package com.baeldung.kotlingradleopenapi

import com.baeldung.car.api.CarsApi
import com.baeldung.kotlingradleopenapi.service.CarService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort

import com.baeldung.car.model.CarBody as ClientCarBody

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CarClientIntegrationTest {
    @LocalServerPort
    var serverPort: Int = 0

    @Autowired
    lateinit var carService: CarService

    @Autowired
    lateinit var carsApi: CarsApi

    @Test
    fun given_spec_client_when_client_method_is_called_then_an_entity_is_created() {
       carsApi.createCar(ClientCarBody(model = "CM-X", make = "Gokyoro", year = 2021)).body?.let {
            assertNotNull(it)
            val entityCar = carService.getCar(it.id)
            assertNotNull(entityCar)
            assertEquals(it.make, entityCar?.make)
            assertEquals(it.model, entityCar?.model)
            assertEquals(it.year, entityCar?.year?.value)
        }

    }
}