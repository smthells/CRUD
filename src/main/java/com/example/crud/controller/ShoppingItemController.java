package com.example.crud.controller;

import com.example.crud.entity.ShoppingItem;
import com.example.crud.service.ShoppingItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для CRUD операций над сущностями {@link ShoppingItem}.
 */
@RestController
@RequestMapping("/api/v1/shopping-items")
public class ShoppingItemController {
    private final ShoppingItemService shoppingItemService;

    public ShoppingItemController(ShoppingItemService shoppingItemService) {
        this.shoppingItemService = shoppingItemService;
    }

    /**
     * Возвращает элемент списка покупок по его идентификатору.
     *
     * @param id Идентификатор элемента списка покупок.
     * @return Объект {@link ResponseEntity} с элементом списка покупок или статусом 404, если элемент не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingItem> getItemById(@PathVariable Long id) {
        return shoppingItemService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Возвращает список всех элементов списка покупок.
     *
     * @return Список {@link ShoppingItem}.
     */
    @GetMapping
    public List<ShoppingItem> getAllItems() {
        return shoppingItemService.findAll();
    }

    /**
     * Создает новый элемент списка покупок.
     *
     * @param shoppingItem Новый элемент списка покупок.
     * @return Созданный элемент {@link ShoppingItem}.
     */
    @PostMapping
    public ShoppingItem createItem(@RequestBody ShoppingItem shoppingItem) {
        return shoppingItemService.save(shoppingItem);
    }

    /**
     * Обновляет существующий элемент списка покупок.
     *
     * @param id           Идентификатор обновляемого элемента.
     * @param shoppingItem Данные для обновления элемента списка покупок.
     * @return Объект {@link ResponseEntity} с обновленным элементом или статусом 404, если элемент не найден.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingItem> updateItem(@PathVariable Long id, @RequestBody ShoppingItem shoppingItem) {
        return shoppingItemService.findById(id)
                .map(itemExist -> {
                    itemExist.setItemName(shoppingItem.getItemName());
                    itemExist.setQuantity(shoppingItem.getQuantity());
                    itemExist.setPrice(shoppingItem.getPrice());
                    return ResponseEntity.ok(shoppingItemService.save(itemExist));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Удаляет элемент списка покупок по его идентификатору.
     *
     * @param id Идентификатор элемента для удаления.
     * @return Объект {@link ResponseEntity} со статусом 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        shoppingItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
