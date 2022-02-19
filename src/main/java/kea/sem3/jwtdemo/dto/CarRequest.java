package kea.sem3.jwtdemo.dto;

import kea.sem3.jwtdemo.entity.CarBrand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {
    @Enumerated(EnumType.STRING)
    private CarBrand brand;
    private String model;
    private Double pricePrDay;
    private Double bestDiscount;
}

