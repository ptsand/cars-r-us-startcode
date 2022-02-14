package kea.sem3.jwtdemo.entity;

import kea.sem3.jwtdemo.dto.ReservationRequest;
import kea.sem3.jwtdemo.error.Client4xxException;
import kea.sem3.jwtdemo.repositories.CarRepository;
import kea.sem3.jwtdemo.repositories.MemberRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne //(fetch = FetchType.LAZY)
    //@JoinColumn(name = "car_id", referencedColumnName = "id")
    Car car;
    @ManyToOne //(fetch = FetchType.LAZY)
    //@JoinColumn(name = "member_id", referencedColumnName = "username")
    Member member;
    @CreationTimestamp
    LocalDateTime reservationDate;
    LocalDateTime rentalDate;
    @UpdateTimestamp
    LocalDateTime edited;

    public Reservation(Car car, Member member, LocalDateTime rentalDate) {
        this.car = car;
        this.member = member;
        this.rentalDate = rentalDate;
    }

    public Reservation(ReservationRequest body, CarRepository carRepository, MemberRepository memberRepository) {
        this.rentalDate = body.getRentalDate();
        this.car = carRepository.findById(body.getCarId()).orElseThrow(()->new Client4xxException("car not found"));
        this.member = memberRepository.findById(body.getMemberId()).orElseThrow(()->new Client4xxException("member not found"));
    }
}
