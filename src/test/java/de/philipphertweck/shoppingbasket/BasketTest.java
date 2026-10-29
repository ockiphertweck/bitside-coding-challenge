package de.philipphertweck.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.philipphertweck.shoppingbasket.deal.BuyOneGetOneFreeDeal;
import de.philipphertweck.shoppingbasket.deal.PercentOffDeal;
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

    @Test
    void rejectTwoDealsForSameArticle() {
        PercentOffDeal deal1 = new PercentOffDeal("A0001", 10);
        BuyOneGetOneFreeDeal deal2 = new BuyOneGetOneFreeDeal("A0001");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Basket(inventory, List.of(deal1, deal2)));

        assertEquals("More than one deal for article A0001", exception.getMessage());
    }

    @Test
    void exampleBasketTestWithBuy1Get1FreeDeal() {
        BuyOneGetOneFreeDeal deal = new BuyOneGetOneFreeDeal("A0002");
        Basket basketWithDeal = new Basket(inventory, List.of(deal));

        basketWithDeal.scan("A0001");
        basketWithDeal.scan("A0002");
        basketWithDeal.scan("A0002");

        // Task example: 16.98 EUR (buy one get one free on A0002)
        assertEquals(1698, basketWithDeal.total());
    }

    @Test
    void exampleBasketTestWithPercentOffDeal() {
        PercentOffDeal deal = new PercentOffDeal("A0001", 10);
        Basket basketWithDeal = new Basket(inventory, List.of(deal));
        basketWithDeal.scan("A0002");
        basketWithDeal.scan("A0001");
        basketWithDeal.scan("A0002");
        // Task example: 19.67 EUR (A0001 at 10 % off is 1169 cents, rounded down)
        assertEquals(1967, basketWithDeal.total());
    }
}