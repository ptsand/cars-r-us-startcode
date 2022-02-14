package kea.sem3.jwtdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.entity.CarBrand;
import kea.sem3.jwtdemo.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarResponse {
    int id;
    @Enumerated(EnumType.STRING)
    CarBrand brand;
    String model;
    double pricePrDay;
    Double bestDiscount;
    Set<Reservation> reservations;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
    LocalDateTime created;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",shape = JsonFormat.Shape.STRING)
    LocalDateTime updated;

    public CarResponse(Car car, boolean includeAll) {
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.pricePrDay = car.getPricePrDay();
        this.id = car.getId();
        if(includeAll) {
            this.created = car.getCreated();
            this.updated = car.getEdited();
            this.bestDiscount = car.getBestDiscount();
            this.reservations = car.getReservations();
        }
    }

    public static List<CarResponse> getCarsFromEntities(List<Car> cars){
        return cars.stream().map(car -> new CarResponse(car,false)).collect(Collectors.toList());
    }
}

