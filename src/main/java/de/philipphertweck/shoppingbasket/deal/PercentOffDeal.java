package de.philipphertweck.shoppingbasket.deal;

/** A deal that reduces an article's unit price by a percentage. */
public class PercentOffDeal extends BaseDeal {

    private final int percentOff;

    /**
     * Creates a percentage-off deal for an article.
     *
     * @param articleNumber the article number to which the deal applies
     * @param percentOff    the discount percentage, from 0 to 100 inclusive
     * @throws IllegalArgumentException if {@code percentOff} is outside that range
     */
    public PercentOffDeal(String articleNumber, int percentOff) {
        super(articleNumber);
        if (percentOff < 0 || percentOff > 100) {
            throw new IllegalArgumentException("Percent off must be between 0 and 100");
        }
        this.percentOff = percentOff;
    }

    /**
     * Calculates the total price after applying the discount to each unit,
     * rounded down to the nearest cent per unit.
     *
     * @param unitPriceInCents the price of one unit in cents
     * @param quantity         the number of units
     * @return the discounted total price in cents
     */
    @Override
    protected long calculate(long unitPriceInCents, int quantity) {
        long discountedPrice = unitPriceInCents * (100 - percentOff) / 100;
        return discountedPrice * quantity;
    }

}
