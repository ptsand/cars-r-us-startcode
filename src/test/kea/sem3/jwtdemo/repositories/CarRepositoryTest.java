package kea.sem3.jwtdemo.repositories;

import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.entity.CarBrand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;
    static int id1;

    @BeforeAll
    static void setUp(@Autowired CarRepository carRepository) {
        id1 = carRepository.save(new Car(CarBrand.BMW,"i3",500.0,300.0)).getId();
    }

    @Test
    void testFindById() {
        Car testCar = carRepository.findById(id1).orElse(null);
        assertEquals(id1,testCar.getId());
    }


}