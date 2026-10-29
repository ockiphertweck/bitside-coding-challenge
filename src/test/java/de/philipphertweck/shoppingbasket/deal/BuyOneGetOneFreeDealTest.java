package de.philipphertweck.shoppingbasket.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.philipphertweck.shoppingbasket.Article;

public class BuyOneGetOneFreeDealTest {

    @Test
    void discountNotAppliedForZeroItems() {
        BuyOneGetOneFreeDeal deal = new BuyOneGetOneFreeDeal("A0002");

        assertEquals(0, deal.priceFor(new Article("A0002", 399), 0));
    }

    @Test
    void discountNotAppliedForSingleItem() {
        BuyOneGetOneFreeDeal deal = new BuyOneGetOneFreeDeal("A0002");

        assertEquals(399, deal.priceFor(new Article("A0002", 399), 1));
    }

    @Test
    void calculatesDiscountedPrice() {
        BuyOneGetOneFreeDeal deal = new BuyOneGetOneFreeDeal("A0002");

        assertEquals(399, deal.priceFor(new Article("A0002", 399), 2));
    }

    @Test
    void chargesUnpairedItemInFull() {
        BuyOneGetOneFreeDeal deal = new BuyOneGetOneFreeDeal("A0002");

        assertEquals(798, deal.priceFor(new Article("A0002", 399), 3));
    }

    @Test
    void discountApplyForMultiplePairs() {
        BuyOneGetOneFreeDeal deal = new BuyOneGetOneFreeDeal("A0002");

        assertEquals(798, deal.priceFor(new Article("A0002", 399), 4));
    }

}
