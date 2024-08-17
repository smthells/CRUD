package com.example.crud.util;

import com.example.crud.entity.ShoppingItem;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Утилитный класс для форматирования и логирования объектов {@link ShoppingItem}.
 */
@Slf4j
public class ShoppingItemFormatForLog {
    private ShoppingItemFormatForLog() {
    }

    /**
     * Форматирует информацию о {@link ShoppingItem} для логирования.
     *
     * @param shoppingItem объект {@link ShoppingItem}, информацию о котором нужно отформатировать.
     * @return строку с отформатированной информацией о покупке.
     */
    public static String formatForLog(ShoppingItem shoppingItem) {
        return String.format("ShoppingItem(itemName=%s, quantity=%d, price=%.2f)",
                shoppingItem.getItemName(), shoppingItem.getQuantity(), shoppingItem.getPrice());
    }

    /**
     * Логирует информацию о найденной или ненайденной покупке по идентификатору.
     * <p>
     * Если покупка с указанным идентификатором найдена, выводится информационное сообщение. Если не найдена,
     * выводится предупреждающее сообщение.
     * </p>
     *
     * @param id           идентификатор покупки.
     * @param shoppingItem объект {@link Optional} содержащий {@link ShoppingItem} если покупка найдена.
     */
    public static void checkForFindById(Long id, Optional<ShoppingItem> shoppingItem) {
        if (shoppingItem.isPresent()) {
            log.info("Найдена покупка с номером: {}", id);
        } else {
            log.warn("Покупка с номером {} не найдена", id);
        }
    }
}
