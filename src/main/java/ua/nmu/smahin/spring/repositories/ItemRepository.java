package ua.nmu.smahin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nmu.smahin.spring.models.ItemModel;

import java.util.Optional;


public interface ItemRepository extends JpaRepository<ItemModel, Long> {
    Optional<ItemModel> findByName(String name);
}