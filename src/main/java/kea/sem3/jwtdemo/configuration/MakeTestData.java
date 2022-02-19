package kea.sem3.jwtdemo.configuration;

import kea.sem3.jwtdemo.entity.*;
import kea.sem3.jwtdemo.repositories.CarRepository;
import kea.sem3.jwtdemo.repositories.MemberRepository;
import kea.sem3.jwtdemo.repositories.ReservationRepository;
import kea.sem3.jwtdemo.security.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@Profile("!test")
public class MakeTestData implements ApplicationRunner {

    UserRepository userRepository;
    MemberRepository memberRepository;
    CarRepository carRepository;
    ReservationRepository reservationRepository;

    public MakeTestData(UserRepository userRepository, MemberRepository memberRepository, CarRepository carRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public void makePlainUsers(){
        BaseUser user = new BaseUser("user", "user@a.dk", "test12");
        user.addRole(Role.USER);
        BaseUser admin = new BaseUser("admin", "admin@a.dk", "test12");
        admin.addRole(Role.ADMIN);

        BaseUser both = new BaseUser("user_admin", "both@a.dk", "test12");
        both.addRole(Role.USER);
        both.addRole(Role.ADMIN);

        userRepository.save(user);
        userRepository.save(admin);
        userRepository.save(both);

        System.out.println("########################################################################################");
        System.out.println("########################################################################################");
        System.out.println("#################################### WARNING ! #########################################");
        System.out.println("## This part breaks a fundamental security rule -> NEVER ship code with default users ##");
        System.out.println("########################################################################################");
        System.out.println("########################  REMOVE BEFORE DEPLOYMENT  ####################################");
        System.out.println("########################################################################################");
        System.out.println("########################################################################################");
        System.out.println("Created TEST Users");
    }

    public void makeReservations() {
        Car testCar = carRepository.save(new Car(CarBrand.FORD,"Fiesta",1000.0,10.0));
        //carRepository.save(new Car(CarBrand.BMW,"i5",1000.0,50.0));
        //carRepository.save(new Car(CarBrand.SUZUKI,"Alto",1000.0,5.0));
        Member testMember = memberRepository.save(new Member("tusern1","e@mail.test","password","firstn","lastn","street","city",2222,false,0));
        // memberRepository.save(new Member("tuserna","ee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",2223,false,0));
        reservationRepository.save(new Reservation(testCar, testMember, LocalDate.of(2000,12,31)));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        userRepository.deleteAll();

        makePlainUsers();
        makeReservations();


    }
}
