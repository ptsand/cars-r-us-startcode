package kea.sem3.jwtdemo.repositories;

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

    @BeforeAll
    static void setUp(@Autowired ReservationRepository reservationRepository) {
        id1 = reservationRepository.save(new Reservation(LocalDateTime.of(2000,12,31,0,0),
                LocalDateTime.of(2000,12,31,0,0))).getId();
    }

    @Test
    void testFindById() {
        Reservation reservation = reservationRepository.findById(id1).orElse(null);
        assertEquals(id1,reservation.getId());
    }


}