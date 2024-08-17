package com.example.crud;

import com.example.crud.entity.ShoppingItem;
import com.example.crud.repository.ShoppingItemRepository;
import com.example.crud.service.ShoppingItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ShoppingItemServiceTest {
    @Mock
    private ShoppingItemRepository shoppingItemRepository;

    @InjectMocks
    private ShoppingItemService shoppingItemService;

    @Test
    public void testFindById() {
        Long id = 1L;
        when(shoppingItemRepository.findById(id)).thenReturn
                (Optional.of(new ShoppingItem(id, "Молоко", 2, BigDecimal.valueOf(89.99))));

        Optional<ShoppingItem> item = shoppingItemService.findById(id);
        assertTrue(item.isPresent());
        assertEquals("Молоко", item.get().getItemName());
        assertEquals(2, item.get().getQuantity());
    }

    @Test
    public void testSave() {
        ShoppingItem item = new ShoppingItem(null, "Молоко", 1, BigDecimal.valueOf(89.99));
        when(shoppingItemRepository.save(item)).thenReturn
                (new ShoppingItem(1L, "Молоко", 1, BigDecimal.valueOf(89.99)));

        ShoppingItem savedItem = shoppingItemService.save(item);
        assertNotNull(savedItem.getId());
        assertEquals("Молоко", savedItem.getItemName());
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        when(shoppingItemRepository.findById(id)).thenReturn(
                Optional.of(new ShoppingItem(id, "Молоко", 2, BigDecimal.valueOf(89.99))));

        shoppingItemService.deleteById(id);

        verify(shoppingItemRepository, times(1)).deleteById(id);
    }
}
