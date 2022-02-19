package kea.sem3.jwtdemo.service;

import kea.sem3.jwtdemo.dto.MemberRequest;
import kea.sem3.jwtdemo.dto.MemberResponse;
import kea.sem3.jwtdemo.entity.Member;
import kea.sem3.jwtdemo.error.Client4xxException;
import kea.sem3.jwtdemo.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public List<MemberResponse> getMembers(){
        return MemberResponse.getMembersFromEntities(memberRepository.findAll());
    }
    public MemberResponse getMember(int id, boolean all) throws Exception {
        //Member car = memberRepository.findById(id).orElseThrow(()->new Client4xxException("Not found"));
        return null; // new MemberResponse(car, false);
    }
    public MemberResponse addMember(MemberRequest body, boolean all){
        return new MemberResponse(memberRepository.save(new Member(body)),all);
    }
    public MemberResponse editMember(MemberRequest body, String id){
        Member m = memberRepository.findById(id).orElseThrow(()->new Client4xxException("Not found"));
        m.setUsername(body.getUsername());
        // to prevent unintentionally changing the member's password to a bcrypt hash
        if (!m.getPassword().equals(body.getPassword())) m.setPassword(body.getPassword());
        m.setEmail(body.getEmail());
        m.setEnabled(body.isEnabled());
        m.setFirstName(body.getFirstName());
        m.setLastName(body.getLastName());
        m.setStreet(body.getStreet());
        m.setCity(body.getCity());
        m.setZip(body.getZip());
        m.setApproved(body.isApproved());
        m.setRanking(body.getRanking());
        return new MemberResponse(memberRepository.save(m),true);
    }
    public void deleteMember(String id) {
        Member member = memberRepository.findById(id).orElseThrow(()->new Client4xxException("Not found"));
        memberRepository.delete(member);
    }

    public MemberResponse getMember(String id, boolean all) {
        Member member = memberRepository.findById(id).orElseThrow(()->new Client4xxException("Not found"));
        return new MemberResponse(member,false);
    }
}

