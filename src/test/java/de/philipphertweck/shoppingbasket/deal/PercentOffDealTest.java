package de.philipphertweck.shoppingbasket.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.philipphertweck.shoppingbasket.Article;

public class PercentOffDealTest {

    @Test
    void calculatesDiscountedPrice() {
        PercentOffDeal deal = new PercentOffDeal("A0001", 20);

        assertEquals(240, deal.priceFor(new Article("A0001", 100), 3));
    }

    @Test
    void rejectPercentageDealWithMoreThan100Percent() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new PercentOffDeal("A0001", 101));
        assertEquals("Percent off must be between 0 and 100", exception.getMessage());
    }

    @Test
    void rejectPercentageDealWithLessThan0Percent() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new PercentOffDeal("A0001", -1));
        assertEquals("Percent off must be between 0 and 100", exception.getMessage());
    }

    @Test
    void roundDownPerUnit() {
        PercentOffDeal deal = new PercentOffDeal("A0001", 10);
        assertEquals(1795, deal.priceFor(new Article("A0001", 1995), 1));
    }

    @Test
    void zeroPercentKeepsPriceTheSame() {
        PercentOffDeal deal = new PercentOffDeal("A0001", 0);
        assertEquals(200, deal.priceFor(new Article("A0001", 100), 2));
    }

    @Test
    void hundredPercentReducesPriceToZero() {
        PercentOffDeal deal = new PercentOffDeal("A0001", 100);
        assertEquals(0, deal.priceFor(new Article("A0001", 100), 2));
    }

    @Test
    void roundsPerUnitNotOnTotal() {
        PercentOffDeal deal = new PercentOffDeal("A0001", 10);

        assertEquals(3590, deal.priceFor(new Article("A0001", 1995), 2));
    }

}
