package kea.sem3.jwtdemo.repositories;

import kea.sem3.jwtdemo.entity.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeAll
    static void setUp(@Autowired MemberRepository memberRepository) {
        memberRepository.save(new Member("usern","e@mail.test","password","firstn","lastn","street","city",2222,false,0));
        memberRepository.save(new Member("userna","ee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",2223,false,0));
    }

    @Test
    public void testCount() {
        assertEquals(2, memberRepository.count());
    }

    @Test
    public void testFindById(){
        Member m = memberRepository.findById("usern").orElse(null);
        assertEquals("e@mail.test",m.getEmail());
    }
    @Test
    public void testEditEmail(){
        Member editedMember = memberRepository.save(new Member("usern","newE@mail.test","password","blfirstn","lastn","street","city",2222,false,0));
        Member m = memberRepository.findById("usern").orElse(null);
        assertEquals(editedMember.getEmail(),m.getEmail());
    }
}