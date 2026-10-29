package de.philipphertweck.shoppingbasket.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.philipphertweck.shoppingbasket.Article;

class BaseDealTest {

    private static class TestDeal extends BaseDeal {

        TestDeal(String articleNumber) {
            super(articleNumber);
        }

        @Override
        protected long calculate(long unitPriceInCents, int quantity) {
            return unitPriceInCents * quantity;
        }
    }

    @Test
    void passesPriceAndQuantityToCalculate() {
        TestDeal deal = new TestDeal("A0001");

        assertEquals(300, deal.priceFor(new Article("A0001", 100), 3));
    }

    @Test
    void rejectsArticleWithDifferentNumber() {
        TestDeal deal = new TestDeal("A0001");

        assertThrows(IllegalArgumentException.class,
                () -> deal.priceFor(new Article("A0002", 399), 1));
    }
}