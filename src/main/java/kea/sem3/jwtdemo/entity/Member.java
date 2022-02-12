package kea.sem3.jwtdemo.entity;

import kea.sem3.jwtdemo.dto.MemberRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@DiscriminatorValue("MEMBER")
public class Member extends BaseUser {

    String firstName;
    String lastName;
    String street;
    String city;
    int zip;
    boolean approved;
    int ranking;

    @CreationTimestamp
    LocalDateTime created;

    @UpdateTimestamp
    LocalDateTime edited;

    public Member(String username, String email, String password, String firstName, String lastName, String street, String city, int zip, boolean approved, int ranking) {
        super(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.approved = approved;
        this.ranking = ranking;
    }

    public Member() {

    }

    public Member(MemberRequest body) {
        super(body.getUsername(), body.getEmail(), body.getPassword());
        this.firstName = body.getFirstName();
        this.lastName = body.getLastName();
        this.street = body.getStreet();
        this.city = body.getCity();
        this.zip = body.getZip();
        this.approved = body.isApproved();
        this.ranking = body.getRanking();
    }
}
