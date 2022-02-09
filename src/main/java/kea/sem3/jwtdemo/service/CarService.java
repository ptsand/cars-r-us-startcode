package kea.sem3.jwtdemo.service;

import kea.sem3.jwtdemo.dto.CarRequest;
import kea.sem3.jwtdemo.dto.CarResponse;
import kea.sem3.jwtdemo.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    CarRepository carRepository;
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    public List<CarResponse> getCars(){
        return null;
    }
    public CarResponse getCar(int id,boolean all) throws Exception {
        return null;
    }
    public CarResponse addCar(CarRequest body){
        return null;
    }
    public CarResponse editCar(CarRequest body,int id){
        return null;
    }
    public void deleteCar(int id) {
        // return null;
    }
}

