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
    public MemberResponse addMember(MemberRequest body){
        return new MemberResponse(memberRepository.save(new Member(body)),true);
    }
    public MemberResponse editMember(MemberRequest body,int id){
        return null;
    }
    public void deleteMember(int id) {
        // return null;
    }
    public MemberResponse getMember(String username) {
        Member member = memberRepository.findById(username).orElseThrow(()->new Client4xxException("Not found"));
        return new MemberResponse(member,false);
    }
}

