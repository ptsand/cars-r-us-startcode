package kea.sem3.jwtdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kea.sem3.jwtdemo.dto.CarRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    CarBrand brand;
    String model;
    Double pricePrDay;
    Double bestDiscount;

    @JsonIgnore
    @OneToMany(mappedBy = "car") // the following side
    private Set<Reservation> reservations = new HashSet<>();

    @CreationTimestamp LocalDateTime created;
    @UpdateTimestamp LocalDateTime edited;

    public Car(CarBrand brand, String model, Double pricePrDay, Double bestDiscount) {
        this.brand = brand;
        this.model = model;
        this.pricePrDay = pricePrDay;
        this.bestDiscount = bestDiscount;
    }
    public Car(CarRequest body) {
        this.brand = body.getBrand();
        this.model = body.getModel();
        this.pricePrDay = body.getPricePrDay();
        this.bestDiscount = body.getBestDiscount();
    }
    public void addReservation(Reservation res){
        res.getCar().getReservations().add(res);
    }
}
