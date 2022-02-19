package kea.sem3.jwtdemo.service;

import kea.sem3.jwtdemo.dto.CarRequest;
import kea.sem3.jwtdemo.dto.CarResponse;
import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.error.Client4xxException;
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
        return CarResponse.getCarsFromEntities(carRepository.findAll());
    }
    public CarResponse getCar(int id, boolean all) throws Client4xxException {
        Car car = carRepository.findById(id).orElseThrow(()->new Client4xxException("Not found"));
        return new CarResponse(car, all);
    }
    public CarResponse addCar(CarRequest body, boolean all){
        return new CarResponse(carRepository.save(new Car(body)),all);
    }
    // Handle put AND patch request
    public CarResponse editCar(CarRequest body, int id){
        Car c = carRepository.findById(id).orElseThrow(()->new Client4xxException("Not found"));
        if(body.getBrand() != null) c.setBrand(body.getBrand());
        if(body.getModel() != null) c.setModel(body.getModel());
        if(body.getPricePrDay() != null) c.setPricePrDay(body.getPricePrDay());
        if(body.getBestDiscount() != null) c.setBestDiscount(body.getBestDiscount());
        return new CarResponse(carRepository.save(c),false);
    }

    public void deleteCar(int id) {
        Car car = carRepository.findById(id).orElseThrow(()->new Client4xxException("Not found"));
        carRepository.delete(car);
    }

    public void editPrice(int id, double newPrice) {
        // TODO: handle patch request
    }
}

