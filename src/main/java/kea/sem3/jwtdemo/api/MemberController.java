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

    // Should be accessible for the admin and the user who owns the id
    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable String id) throws Exception {
        return memberService.getMember(id,true);
    }
    // Should be accessible for the admin
    @GetMapping
    public List<MemberResponse> getMembers(){
        return memberService.getMembers();
    }
    // Should be accessible for the admin and other users
    @PostMapping
    public MemberResponse addMember(@RequestBody MemberRequest body){
        return memberService.addMember(body, false);
    }
    // Should be accessible for the admin and the user who owns the id
    @PutMapping("/{id}")
    public MemberResponse editMember(@RequestBody MemberRequest body, @PathVariable String id){
        return memberService.editMember(body,id);
    }
    // Should be accessible for the admin and the user who owns the id
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable String id){
        memberService.deleteMember(id);
    }

}

