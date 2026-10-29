package de.philipphertweck.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void findsAddedArticle() {
        Article article = new Article("123", 100);
        inventory.addArticle(article);

        assertEquals(Optional.of(article), inventory.findArticle("123"));
    }

    @Test
    void returnsEmptyForUnknownArticleNumber() {
        assertTrue(inventory.findArticle("123").isEmpty());
    }

    @Test
    void rejectsDuplicateArticleNumber() {
        Article original = new Article("123", 100);
        inventory.addArticle(original);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> inventory.addArticle(new Article("123", 200)));

        assertEquals("Article with number 123 already exists", exception.getMessage());

        assertEquals(Optional.of(original), inventory.findArticle("123"));
    }
}