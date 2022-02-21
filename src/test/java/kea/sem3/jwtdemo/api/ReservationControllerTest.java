package kea.sem3.jwtdemo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.sem3.jwtdemo.dto.ReservationRequest;
import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.entity.CarBrand;
import kea.sem3.jwtdemo.entity.Member;
import kea.sem3.jwtdemo.entity.Reservation;
import kea.sem3.jwtdemo.error.Client4xxException;
import kea.sem3.jwtdemo.repositories.CarRepository;
import kea.sem3.jwtdemo.repositories.MemberRepository;
import kea.sem3.jwtdemo.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired ReservationRepository reservationRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CarRepository carRepository;
    @Autowired MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    private Car testCar;
    private Member testMember;
    private List<Reservation> reservations = new ArrayList<>();
    private static LocalDate availableDate = LocalDate.of(2023,12,10);

    @BeforeEach
    public void setup() {
        reservationRepository.deleteAll();
        testCar = carRepository.save(new Car(CarBrand.FORD,"Fiesta",1000.0,20.0));
        testMember = memberRepository.save(new Member("tuserna","ee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",2223,false,0));
        reservations.add(reservationRepository.save(new Reservation(testCar, testMember, LocalDate.of(2023,12,1))));
        reservations.add(reservationRepository.save(new Reservation(testCar, testMember, LocalDate.of(2023,11,1))));
        reservations.add(reservationRepository.save(new Reservation(testCar, testMember, LocalDate.of(2023,10,1))));
    }

    @Test
    public void testReservationById() throws Exception {
        Reservation res = reservations.get(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservations/" + res.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(res.getId()));
        // Reservation r = reservationRepository.findById(res.getId()).orElseThrow(()->new Client4xxException("reservation not found"));
        Member m = memberRepository.findById(testMember.getUsername()).orElseThrow(()->new Client4xxException("Member not found"));
        assertEquals(m.getReservations().size(),reservations.size()); // Same member owns all reservations
    }

    @Test
    public void testAllReservations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservations")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(reservations.size()));
    }

    @Test
    public void testMakeReservationAvailableCar() throws Exception {
        ReservationRequest resReq = new ReservationRequest(availableDate, testCar, testMember);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(resReq)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        // Verify that it actually ended in the database
        assertEquals(reservations.size()+1, reservationRepository.count());
        // Verify the reservation got added to the testCar reservations HashSet
        // Find by id with entity graph to eagerly load reservations
        Car car = carRepository.findById(testCar.getId()).orElseThrow(()->new Client4xxException("car not found"));
        assertEquals(reservations.size()+1, car.getReservations().size()); // All reservations are on the same car
    }

    @Test
    public void testMakeReservationUnavailableCar() throws Exception {
        ReservationRequest resReq = new ReservationRequest(reservations.get(0).getRentalDate(), testCar, testMember);
        Exception thrown = assertThrows(
                Exception.class,
                () -> mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(resReq))),
                "Expected mockMvc to throw Exception, but it didn't"
        );
        //Verify that it didn't end in the database
        assertEquals(reservations.size(), reservationRepository.count());
    }

    @Test
    public void editReservation() throws Exception {
    }

    @Test
    void deleteReservation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/reservations/"+ reservations.get(0).getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(reservations.size()-1, reservationRepository.count());
    }
}

