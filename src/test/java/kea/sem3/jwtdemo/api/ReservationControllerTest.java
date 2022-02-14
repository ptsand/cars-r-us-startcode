package kea.sem3.jwtdemo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.sem3.jwtdemo.dto.ReservationRequest;
import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.entity.CarBrand;
import kea.sem3.jwtdemo.entity.Member;
import kea.sem3.jwtdemo.entity.Reservation;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CarRepository carRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Car testCar;
    private Member testMember;
    private Reservation reservation;

    @BeforeEach
    public void setup() {
        reservationRepository.deleteAll();
        testCar = carRepository.save(new Car(CarBrand.FORD,"Fiesta",1000.0,20.0));
        testMember = memberRepository.save(new Member("tuserna","ee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",2223,false,0));
        reservation = reservationRepository.save(new Reservation(testCar, testMember, LocalDateTime.of(2000,12,31,0,0)));
    }

    @Test
    public void testReservationById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/reservation/" + reservation.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(reservation.getId()));
    }

    @Test
    public void testAllReservations() throws Exception {
        String model = "$[?(@.model == '%s')]";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/cars")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(model, "Fiesta").exists());
    }

    @Test
    public void testMakeReservation() throws Exception {
        ReservationRequest resReq = new ReservationRequest(LocalDateTime.of(2022,10,10,10,1),testCar.getId(),testMember.getUsername());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(resReq)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        //Verify that it actually ended in the database
        assertEquals(2, reservationRepository.count());
    }

    // @Test
    public void editCar() throws Exception {
    }

    @Test
    void deleteCar() throws Exception {
    }
}

