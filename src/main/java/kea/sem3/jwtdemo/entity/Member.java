package kea.sem3.jwtdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kea.sem3.jwtdemo.dto.MemberRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
// @DiscriminatorValue("MEMBER")
public class Member extends BaseUser {
    @Column(length = 45) String firstName;
    @Column(length = 65) String lastName;
    @Column(length = 70) String street;
    @Column(length = 35) String city;
    int zip;
    boolean approved;
    int ranking; // 0-10
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    Set<Reservation> reservations = new HashSet<>();
    @CreationTimestamp LocalDateTime created;
    @UpdateTimestamp LocalDateTime edited;

    public Member() {}

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
