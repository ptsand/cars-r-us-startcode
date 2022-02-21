package kea.sem3.jwtdemo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.sem3.jwtdemo.dto.MemberRequest;
import kea.sem3.jwtdemo.entity.Member;
import kea.sem3.jwtdemo.repositories.MemberRepository;
import kea.sem3.jwtdemo.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    private List<Member> tms = new ArrayList<>();
    @Autowired private MemberRepository memberRepository;

    @BeforeEach // @BeforeAll creates transactional issues
    public void setup(@Autowired ReservationRepository resRepos) {
        resRepos.deleteAll();
        memberRepository.deleteAll();
        tms.add(memberRepository.save(new Member("tusrname","e@mail.test","password","firstn","lastn","street","city",2222,true,0)));
        tms.add(memberRepository.save(new Member("usertest","eee@mail.test","passw0rd","firstna","lastna","sstreet","ccity",2223,true,0)));
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/members/" + tms.get(0).getUsername())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(tms.get(0).getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approved").value(tms.get(0).isApproved()));
    }
    @Test
    public void testGetAll() throws Exception {
        String email = "$[?(@.email == '%s')]";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/members")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(tms.size()))
                .andExpect(MockMvcResultMatchers.jsonPath(email, tms.get(0).getEmail()).exists())
                .andExpect(MockMvcResultMatchers.jsonPath(email, tms.get(0).getEmail()).exists());
    }
    @Test
    public void testEdit() throws Exception {
        int newZipCode = 8210;
        Member m1 = tms.get(0);
        MemberRequest mr = new MemberRequest(m1.getUsername(), m1.getPassword(),m1.getEmail(),m1.isEnabled(), m1.getFirstName(), m1.getLastName(),m1.getStreet(),m1.getCity(),newZipCode,true,0);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/members/"+m1.getUsername())
                .contentType("application/json")
                .accept("application/json")
                .content(objectMapper.writeValueAsString(mr)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.zip").value(newZipCode));
        // Be sure that the members zipcode got updated
        Member m = memberRepository.findById(m1.getUsername()).orElse(null);
        assertEquals(m.getZip(),newZipCode);
    }
    //@Test
    public void testAdd() throws Exception {
        MemberRequest mr = new MemberRequest("tm","secret","t@m.test",true,"fn","ln","s","c",9000,true,0);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                        .contentType("application/json")
                        .accept("application/json")
                        .content(objectMapper.writeValueAsString(mr)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tm"));
        //Verify that it actually ended in the database
        assertEquals(tms.size()+1, memberRepository.count());
    }
    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/members/"+tms.get(0).getUsername()))
                .andExpect(status().isOk());
        // Be sure the member is gone
        assertNull(memberRepository.findById(tms.get(0).getUsername()).orElse(null));
    }
}

