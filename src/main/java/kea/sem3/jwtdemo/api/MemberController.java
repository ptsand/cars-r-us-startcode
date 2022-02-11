package kea.sem3.jwtdemo.api;

import kea.sem3.jwtdemo.dto.MemberRequest;
import kea.sem3.jwtdemo.dto.MemberResponse;
import kea.sem3.jwtdemo.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/members")
public class MemberController {
    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable String id) throws Exception {
        return memberService.getMember(id);
    }

    @GetMapping
    public List<MemberResponse> getMembers(){
        return memberService.getMembers();
    }

    @PostMapping
    public MemberResponse addMember(@RequestBody MemberRequest body){
        return memberService.addMember(body);
    }

    @PutMapping("/{id}")
    public MemberResponse editMembers(@RequestBody MemberRequest body, @PathVariable String id){return null;}

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable String id){}

}

