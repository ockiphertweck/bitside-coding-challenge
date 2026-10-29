package de.philipphertweck.shoppingbasket.deal;

/**
 * Applies a buy-one-get-one-free deal to an article.
 */
public class BuyOneGetOneFreeDeal extends BaseDeal {

    /**
     * Creates a deal for the specified article.
     *
     * @param articleNumber the article number to which the deal applies
     */
    public BuyOneGetOneFreeDeal(String articleNumber) {
        super(articleNumber);
    }

    /**
     * Calculates the cost by charging for every other item, rounding up.
     *
     * @param unitPriceInCents the price of one item in cents
     * @param quantity         the number of items
     * @return the total cost in cents
     */
    @Override
    protected long calculate(long unitPriceInCents, int quantity) {
        int chargeableQuantity = (quantity + 1) / 2;
        return unitPriceInCents * chargeableQuantity;
    }

}
