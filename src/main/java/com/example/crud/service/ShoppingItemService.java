package com.example.crud.service;

import com.example.crud.entity.ShoppingItem;
import com.example.crud.repository.ShoppingItemRepository;
import com.example.crud.util.ShoppingItemFormatForLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.crud.util.ShoppingItemFormatForLog.checkForFindById;

/**
 * Класс сервис для выполнения операций над сущностями {@link ShoppingItem}.
 */
@Service
@Slf4j
public class ShoppingItemService {
    private final ShoppingItemRepository shoppingItemRepository;

    public ShoppingItemService(ShoppingItemRepository shoppingItemRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
    }

    public Optional<ShoppingItem> findById(Long id) {
        Optional<ShoppingItem> shoppingItem = shoppingItemRepository.findById(id);
        checkForFindById(id, shoppingItem);
        return shoppingItem;
    }

    public List<ShoppingItem> findAll() {
        List<ShoppingItem> shoppingItems = shoppingItemRepository.findAll();
        log.info("Найдено покупок: {}", shoppingItems.size());
        return shoppingItems;
    }

    public ShoppingItem save(ShoppingItem shoppingItem) {
        log.info("Сохраняется покупка: {}", ShoppingItemFormatForLog.formatForLog(shoppingItem));
        ShoppingItem savedShoppingItem = shoppingItemRepository.save(shoppingItem);
        log.info("Покупка сохранена с номером: {}", savedShoppingItem.getId());
        return savedShoppingItem;
    }

    public void deleteById(Long id) {
        Optional<ShoppingItem> shoppingItem = shoppingItemRepository.findById(id);
        if (shoppingItem.isPresent()) {
            shoppingItemRepository.deleteById(id);
            log.info("Удалена покупка с номером: {}", id);
        } else {
            log.warn("Попытка удалить покупку с номером {}, которой не существует", id);
        }
    }
}
