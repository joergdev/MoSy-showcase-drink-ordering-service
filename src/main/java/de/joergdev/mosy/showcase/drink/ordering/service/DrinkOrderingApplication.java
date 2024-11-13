package de.joergdev.mosy.showcase.drink.ordering.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import de.joergdev.mosy.showcase.drink.ordering.service.model.DrinkOrder;
import de.joergdev.mosy.showcase.drink.ordering.service.persistence.DrinkOrderRepository;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class DrinkOrderingApplication
{
  private final DrinkOrderRepository drinkOrderRepository;

  @Autowired
  public DrinkOrderingApplication(DrinkOrderRepository drinkOrderRepository)
  {
    this.drinkOrderRepository = drinkOrderRepository;
  }

  public static void main(String[] args)
  {
    SpringApplication.run(DrinkOrderingApplication.class, args);
  }

  @Bean
  public WebClient.Builder webClientBuilder()
  {
    return WebClient.builder();
  }

  @PostConstruct
  public void initDatabase()
  {
    drinkOrderRepository.save(new DrinkOrder("GinTonic", 4, LocalDate.now().minusDays(1)));
    drinkOrderRepository.save(new DrinkOrder("MaiTai", 2, LocalDate.now()));
  }
}
