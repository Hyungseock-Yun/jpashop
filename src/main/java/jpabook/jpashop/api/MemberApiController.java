package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

  private final MemberService memberService;

  @GetMapping("/api/v1/members")
  public List<Member> membersV1() {
    return memberService.findMembers();
  }

  @GetMapping("/api/v2/members")
  public Result membersV2() {
    List<Member> findMembers = memberService.findMembers();
    List<MemberDto> collect = findMembers.stream()
      .map(m -> new MemberDto(m.getName()))
      .collect(Collectors.toList());
    return new Result(collect.size(), collect);
  }

  @Data
  @AllArgsConstructor
  static class Result<T> {
    private int count;
    private T data;
  }

  @Data
  @AllArgsConstructor
  static class MemberDto {
    private String name;
  }

  /* Entity를 직접 주고받으면 안됨. 노출시켜서도 안됨. */
  @PostMapping("/api/v1/members")
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {   // @RequestBody : JSON으로 들어온 데이터를 지정된 객체로 변환하여 셋팅
    Long id = memberService.join(member);

    return new CreateMemberResponse(id);
  }

  /* Dto를 따로 만들어서 사용하면, Entity가 바뀌어도 API스펙은 변하지 않음 */
  @PostMapping("/api/v2/members")
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
    Member member = new Member();
    member.setName(request.getName());

    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @PutMapping("/api/v2/members/{id}")
  public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                             @RequestBody @Valid UpdateMemberRequest request) {

    memberService.update(id, request.getName());
    Member findMember = memberService.findOne(id);

    return new UpdateMemberResponse(findMember.getId(), findMember.getName());
  }

  @Data
  static class UpdateMemberRequest {
    private String name;
  }

  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse {
    private Long id;
    private String name;
  }

  @Data
  static class CreateMemberRequest {
    @NotEmpty
    private String name;
  }

  @Data
  @AllArgsConstructor
  static class CreateMemberResponse {
    private Long id;
  }

}
