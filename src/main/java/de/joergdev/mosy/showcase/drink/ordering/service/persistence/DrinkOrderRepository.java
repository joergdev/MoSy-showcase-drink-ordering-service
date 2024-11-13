package de.joergdev.mosy.showcase.drink.ordering.service.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.joergdev.mosy.showcase.drink.ordering.service.model.DrinkOrder;

@Repository
public interface DrinkOrderRepository extends JpaRepository<DrinkOrder, Long>
{}
