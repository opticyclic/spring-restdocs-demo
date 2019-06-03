package com.github.opticyclic.restdocs;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Stock {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private BigDecimal price;

  public Stock() {
    //no-op constructor for serialisation
  }

  public Stock(Long id, String name, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;

  }

  @Override
  public String toString() {
    return "Stock{" +
             "id=" + id +
             ", name='" + name + '\'' +
             ", price=" + price +
             '}';
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    Stock stock = (Stock)o;
    return Objects.equals(id, stock.id) &&
             Objects.equals(name, stock.name) &&
             Objects.equals(price, stock.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price);
  }
}
