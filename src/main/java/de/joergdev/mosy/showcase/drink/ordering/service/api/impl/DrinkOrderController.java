package de.joergdev.mosy.showcase.drink.ordering.service.api.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.joergdev.mosy.showcase.drink.ordering.service.model.DrinkOrder;
import de.joergdev.mosy.showcase.drink.ordering.service.persistence.DrinkOrderRepository;

@RestController
@RequestMapping("/api/orders")
public class DrinkOrderController
{
  private final DrinkOrderRepository drinkOrderRepository;

  @Autowired
  public DrinkOrderController(DrinkOrderRepository drinkOrderRepository)
  {
    this.drinkOrderRepository = drinkOrderRepository;
  }

  @GetMapping
  public ResponseEntity<List<DrinkOrder>> getAllOrders()
  {
    List<DrinkOrder> orders = drinkOrderRepository.findAll();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DrinkOrder> getOrderById(@PathVariable Long id)
  {
    return drinkOrderRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<DrinkOrder> createOrder(@RequestBody DrinkOrder order)
  {
    DrinkOrder savedOrder = drinkOrderRepository.save(order);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id)
  {
    drinkOrderRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
