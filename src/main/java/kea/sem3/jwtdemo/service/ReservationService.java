package kea.sem3.jwtdemo.service;

import kea.sem3.jwtdemo.dto.ReservationRequest;
import kea.sem3.jwtdemo.dto.ReservationResponse;
import kea.sem3.jwtdemo.entity.Reservation;
import kea.sem3.jwtdemo.error.Client4xxException;
import kea.sem3.jwtdemo.repositories.CarRepository;
import kea.sem3.jwtdemo.repositories.MemberRepository;
import kea.sem3.jwtdemo.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    ReservationRepository reservationRepository;
    CarRepository carRepository;
    MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
    }
    public List<ReservationResponse> getReservations(){
        return reservationRepository.findAll().stream().map(res -> new ReservationResponse(res)).collect(Collectors.toList());
    }
    public ReservationResponse getReservation(int id) throws Exception {
        Reservation res = reservationRepository.findById(id).orElseThrow(()->new Client4xxException("Not found"));
        return new ReservationResponse(res);
    }
    public ReservationResponse makeReservation(ReservationRequest body){
        // TODO: Throw exception if the car is already reserved at that time
        return new ReservationResponse(reservationRepository.save(new Reservation(body, carRepository, memberRepository)));
    }
    public ReservationResponse editReservation(ReservationRequest body,int id){
        return null;
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }
}

