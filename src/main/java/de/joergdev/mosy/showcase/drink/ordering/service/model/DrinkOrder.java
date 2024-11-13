package de.joergdev.mosy.showcase.drink.ordering.service.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DrinkOrder
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String drinkName;
  private int quantity;
  private LocalDate orderDate;

  public DrinkOrder()
  {}

  public DrinkOrder(String drinkName, int quantity, LocalDate orderDate)
  {
    this.drinkName = drinkName;
    this.quantity = quantity;
    this.orderDate = orderDate;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getDrinkName()
  {
    return drinkName;
  }

  public void setDrinkName(String drinkName)
  {
    this.drinkName = drinkName;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public void setQuantity(int quantity)
  {
    this.quantity = quantity;
  }

  public LocalDate getOrderDate()
  {
    return orderDate;
  }

  public void setOrderDate(LocalDate orderDate)
  {
    this.orderDate = orderDate;
  }
}
