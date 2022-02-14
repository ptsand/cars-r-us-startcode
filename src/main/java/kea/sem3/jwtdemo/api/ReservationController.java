package kea.sem3.jwtdemo.api;

import kea.sem3.jwtdemo.dto.ReservationRequest;
import kea.sem3.jwtdemo.dto.ReservationResponse;
import kea.sem3.jwtdemo.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ReservationResponse getReservation(@PathVariable int id) throws Exception {
        return reservationService.getReservation(id);
    }

    @GetMapping
    public List<ReservationResponse> getReservations(){
        return reservationService.getReservations();
    }

    @PostMapping
    public ReservationResponse addReservation(@RequestBody ReservationRequest body){
        return reservationService.makeReservation(body);
    }

    @PutMapping("/{id}")
    public ReservationResponse editReservation(@RequestBody ReservationRequest body, @PathVariable int id){
        return reservationService.editReservation(body, id);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable int id){ reservationService.deleteReservation(id);}
}

