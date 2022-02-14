package kea.sem3.jwtdemo.repositories;

import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.entity.CarBrand;
import kea.sem3.jwtdemo.entity.Member;
import kea.sem3.jwtdemo.entity.Reservation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;
    static int id1;
    static Car testCar;
    static Member testMember;

    @BeforeAll
    static void setUp(@Autowired ReservationRepository reservationRepository, @Autowired CarRepository carRepository, @Autowired MemberRepository memberRepository) {
        testCar = carRepository.save(new Car(CarBrand.FORD,"Fiesta",1000.0,20.0));
        testMember = memberRepository.save(new Member("tuserna","ee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",2223,false,0));
        id1 = reservationRepository.save(new Reservation(testCar, testMember, LocalDateTime.of(2000,12,31,0,0))).getId();
    }

    @Test
    void testFindById() {
        Reservation reservation = reservationRepository.findById(id1).orElse(null);
        assertEquals(id1,reservation.getId());
    }


}