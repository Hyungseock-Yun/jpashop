//package jpabook.jpashop;
//
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@Repository
//public class MemberRepository {
//
//  @PersistenceContext
//  private EntityManager em;
//
//  public Long save(Member member) {
//    em.persist(member);
//
//    return member.getId();    // command와 쿼리를 분리. 저장 후 사이드이펙트를 일으키는 command성이기 때문에 return값을 안만들고 id만 return.
//  }
//
//  public Member find(Long id) {
//    return em.find(Member.class, id);
//  }
//
//}
