package com.example.crud.repository;

import com.example.crud.entity.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для управления сущностями {@link ShoppingItem}.
 */
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
}
