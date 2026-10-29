package de.philipphertweck.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.philipphertweck.shoppingbasket.exceptions.UnknownArticleException;

class BasketTest {

    private Inventory inventory;
    private Basket basket;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        basket = new Basket(inventory);
        inventory.addArticle(new Article("A0001", 1299));
        inventory.addArticle(new Article("A0002", 399));
    }

    @Test
    void emptyBasketHasZeroTotal() {
        assertEquals(0, basket.total());
    }

    @Test
    void totalWithoutDeals() {
        basket.scan("A0001");
        basket.scan("A0002");
        basket.scan("A0002");

        assertEquals(1299 + 2 * 399, basket.total());
    }

    @Test
    void rejectUnknownArticle() {
        UnknownArticleException exception = assertThrows(
                UnknownArticleException.class,
                () -> basket.scan("999"));

        assertEquals("Article with number 999 does not exist", exception.getMessage());
    }

    @Test
    void basketsAreIndependent() {
        Basket basket2 = new Basket(inventory);

        basket.scan("A0001");

        assertEquals(1299, basket.total());
        assertEquals(0, basket2.total());

        basket2.scan("A0002");
        assertEquals(1299, basket.total());
        assertEquals(399, basket2.total());
    }

}