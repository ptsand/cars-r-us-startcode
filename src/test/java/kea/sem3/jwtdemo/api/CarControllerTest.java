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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarRepository carRepository;
    private List<Car> testCars = new ArrayList<>();

    @BeforeEach
    public void setup() {
        carRepository.deleteAll();
        testCars.add(carRepository.save(new Car(CarBrand.FORD, "Focus", 400.0, 10.0)));
        testCars.add(carRepository.save(new Car(CarBrand.SUZUKI, "Vitara", 500.0, 14.0)));
    }

    @Test
    public void testCarById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/cars/" + testCars.get(0).getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testCars.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(testCars.get(0).getModel()));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(testCars.size()))
                .andExpect(MockMvcResultMatchers.jsonPath(model, testCars.get(0).getModel()).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(model, testCars.get(1).getModel()).exists());
    }

    @Test
    public void testAddCar() throws Exception {
        CarRequest newCar = new CarRequest(CarBrand.WW, "Polo", 200.0, 10.0);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cars")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        //Verify that it actually ended in the database
        assertEquals(testCars.size()+1, carRepository.count());
    }

    // @Test
    public void editCar() throws Exception {
    }

    @Test
    void deleteCar() throws Exception {
    }
}

