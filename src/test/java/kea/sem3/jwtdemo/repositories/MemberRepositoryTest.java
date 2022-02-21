package kea.sem3.jwtdemo.repositories;

import kea.sem3.jwtdemo.entity.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    static List<Member> testMembers = new ArrayList<>();

    @BeforeAll
    static void setUp(@Autowired MemberRepository memberRepository, @Autowired ReservationRepository reservationRepository) {
        reservationRepository.deleteAll();
        memberRepository.deleteAll();
        testMembers.add(memberRepository.save(new Member(
                "usern","e@mail.test","password","firstn","lastn","street","city",2222,false,0)));
        testMembers.add(memberRepository.save(new Member(
                "usernam","eee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",2223,false,0)));
    }

    @Test
    public void testCount() {
        assertEquals(testMembers.size(), memberRepository.count());
    }

    @Test
    public void testFindById(){
        Member m = memberRepository.findById(testMembers.get(0).getUsername()).orElse(null);
        assertEquals(testMembers.get(0).getEmail(), m.getEmail());
    }
    @Test
    public void testEditEmail(){
        Member te = testMembers.get(1);
        Member editedMember = memberRepository.save(new Member(
                te.getUsername(),"newE@mail.test",te.getPassword(),te.getFirstName(),te.getLastName(),te.getStreet(),te.getCity(),te.getZip(),te.isApproved(),te.getRanking()));
        Member m = memberRepository.findById(te.getUsername()).orElse(null); // Ensure it is updated
        assertEquals(editedMember.getEmail(), m.getEmail());
    }
}