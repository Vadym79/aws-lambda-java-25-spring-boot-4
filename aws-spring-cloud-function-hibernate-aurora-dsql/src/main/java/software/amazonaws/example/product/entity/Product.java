
package software.amazonaws.example.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

@Entity
@Table(name = "products")
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(sequenceName = "product_id", allocationSize = 1)
  private int id;
  private String name;
  private int price;

  private static final long serialVersionUID = -4036966741701017325L;
  
  @JsonCreator
  public Product() {
  }

  public Product(int id, String name, int price) {
    this.id = id;
    this.name = name;
    this.price=price;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product{" +
      "id='" + this.id + '\'' +
      ", name='" + this.name + '\'' +
      ", price=" + this.price +
      '}';
  }
}
