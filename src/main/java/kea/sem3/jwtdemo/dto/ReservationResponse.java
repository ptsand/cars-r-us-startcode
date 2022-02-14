package kea.sem3.jwtdemo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.entity.Member;
import kea.sem3.jwtdemo.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationResponse {
    int id;
    LocalDateTime reservationDate;
    LocalDateTime rentalDate;
    Car car;
    Member member;
    LocalDateTime created;
    LocalDateTime edited;

    public ReservationResponse(Reservation res) {
        this.id = res.getId();
        this.reservationDate = res.getReservationDate();
        this.rentalDate = res.getRentalDate();
        this.car = res.getCar();
        this.member = res.getMember();
        this.edited = res.getEdited();
    }
}
