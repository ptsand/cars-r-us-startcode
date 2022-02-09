package kea.sem3.jwtdemo.entity;

import kea.sem3.jwtdemo.dto.CarRequest;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    CarBrand brand;
    String model;
    Double pricePrDay;
    Double bestDiscount;

    @CreationTimestamp
    LocalDateTime created;

    @UpdateTimestamp
    LocalDateTime edited;

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


    public Car() {

    }
}
