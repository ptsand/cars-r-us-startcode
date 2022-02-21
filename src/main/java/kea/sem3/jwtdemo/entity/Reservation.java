package kea.sem3.jwtdemo.entity;

import kea.sem3.jwtdemo.dto.ReservationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    //@JoinColumn(name = "car_id", referencedColumnName = "id")
    Car car;
    @ManyToOne
    //@JoinColumn(name = "member_id", referencedColumnName = "username")
    Member member;
    @CreationTimestamp
    LocalDateTime reservationDate;
    LocalDate rentalDate;
    @UpdateTimestamp
    LocalDateTime edited;

    public Reservation(Car car, Member member, LocalDate rentalDate) {
        this.car = car;
        this.member = member;
        this.rentalDate = rentalDate;
    }

    public Reservation(ReservationRequest body) {
        this.rentalDate = body.getRentalDate();
        this.car = body.getCar();
        this.member = body.getMember();
    }
}
