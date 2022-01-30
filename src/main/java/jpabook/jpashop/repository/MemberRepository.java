package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/* SpringBootApplication - 패키지 및 하위들을 전부 component 스캔하여 spring bean으로 자동 등록함.
 (Repository 내부에 component 설정되어있음) */
@Repository
@RequiredArgsConstructor
public class MemberRepository {

//  @PersistenceContext   // spring bean에 등록된 EntityManager 주입시켜 줌
  private final EntityManager em;

  public void save(Member member) {
    em.persist(member);
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class)
      .getResultList();
  }

  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
      .setParameter("name", name)
      .getResultList();
  }
}
