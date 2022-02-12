package kea.sem3.jwtdemo.service;

import kea.sem3.jwtdemo.dto.MemberRequest;
import kea.sem3.jwtdemo.dto.MemberResponse;
import kea.sem3.jwtdemo.entity.Member;
import kea.sem3.jwtdemo.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MemberServiceMockitoTest {

    @Mock
    MemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    void getMembers() {
        Mockito.when(memberRepository.findAll()).thenReturn(List.of(
                new Member("tusern","e@mail.test","password","firstn","lastn","street","city",2222,false,0),
                new Member("tuserna","ee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",2223,false,0)
        ));
        List<MemberResponse> members = memberService.getMembers();
        assertEquals(2,members.size());
        // TODO: add more tests
    }

    @Test
    void getMember() {
        Member member = new Member("username","ee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",8000,false,0);
        Mockito.when(memberRepository.findById("username")).thenReturn(Optional.of(member));
        MemberResponse memberResponse = memberService.getMember("username", true);
        assertEquals(8000, memberResponse.getZip());
    }

    @Test
    void addMember() {
        Member m = new Member("myUser","ee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",8000,false,0);
        Mockito.when(memberRepository.save(any(Member.class))).thenReturn(m);
        MemberResponse resp = memberService.addMember(
                new MemberRequest(m.getUsername(),m.getPassword(),m.getEmail(),true,m.getFirstName(),
                        m.getLastName(),m.getStreet(),m.getCity(),m.getZip(),m.isApproved(),m.getRanking()), false);
        assertEquals("myUser",resp.getUsername());
    }

    @Test
    void editMember() {
    }

    @Test
    void deleteMember() {

    }

    @Test
    void testGetMember() {
    }
}