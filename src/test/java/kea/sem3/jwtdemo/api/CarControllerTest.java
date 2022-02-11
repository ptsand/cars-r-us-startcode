package kea.sem3.jwtdemo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.sem3.jwtdemo.dto.CarRequest;
import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.entity.CarBrand;
import kea.sem3.jwtdemo.repositories.CarRepository;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static int carFordId, carSuzukiId;

    @BeforeEach
    public void setup() {
        carFordId = carRepository.save(new Car(CarBrand.FORD, "Focus", 400.0, 10.0)).getId();
        carSuzukiId = carRepository.save(new Car(CarBrand.SUZUKI, "Vitara", 500.0, 14.0)).getId();
    }

    @Test
    public void testCarById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/cars/" + carFordId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(carFordId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Focus"));
    }

    @Test
    public void testAllCars() throws Exception {
        String model = "$[?(@.model == '%s')]";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/cars")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                //One way of testing this
                .andExpect(MockMvcResultMatchers.jsonPath(model, "Focus").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(model, "Vitara").exists())
                //Another way
                .andExpect(MockMvcResultMatchers.content().string(containsString("Focus")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Vitara")));
    }

    @Test
    public void testAddCar() throws Exception {
        CarRequest newCar = new CarRequest(CarBrand.WW, "Polo", 200, 10);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cars")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        //Verify that it actually ended in the database
        assertEquals(3, carRepository.count());
    }

    // @Test
    public void editCar() throws Exception {
    }

    @Test
    void deleteCar() throws Exception {
    }
}

