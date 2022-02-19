package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor    // final로 선언된 필드의 생성자를 생성해줌
public class MemberService {

  private final MemberRepository memberRepository;

  // 회원 가입
  public Long join(Member member) {
    validateDuplicateMember(member);
    memberRepository.save(member);

    return member.getId();
  }

  // 중복 회원 검증
  private void validateDuplicateMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName());
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  // 회원 전체 조회
  @Transactional(readOnly = true)   // 읽기에는 가급적 readOnly = true를 쓰는 것이 효율적. 삽입, 갱신에 쓰면 DB에 적용이 안됨.
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  // 회원 1건 조회
  @Transactional(readOnly = true)
  public Member findOne(Long memberId) {
    return memberRepository.findOne(memberId);
  }

  @Transactional
  public void update(Long id, String name) {
    Member member = memberRepository.findOne(id);
    member.setName(name);
  }
}
