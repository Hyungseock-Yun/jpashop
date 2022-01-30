package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

  private String city;

  private String street;

  private String zipcode;

  /* JPA에서 reflection이나 proxy를 써야하는데 기본 생성자가 없으면 불가능하므로 그를 위해 만들어준다.
     protected까지 지원되므로 다른곳에서 생성 못하도록 설정. */
  protected Address() {
  }

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
