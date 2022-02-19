package jpabook.jpashop.repository.order.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

  private final EntityManager em;

  // 화면에 종속적이고 쿼리는 무거울 때 사용.
  // repository는 객체 그래프를 조회할 때 사용해야하나 API에 너무 최적화되어 있어, 스펙이 바뀌면 뜯어 고쳐야함
  public List<OrderSimpleQueryDto> findOrderDtos() {
    return em.createQuery(
        "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
          " from Order o" +
          " join o.member m" +
          " join o.delivery d", OrderSimpleQueryDto.class)
      .getResultList();
  }

}
