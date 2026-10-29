package de.philipphertweck.shoppingbasket.deal;

import de.philipphertweck.shoppingbasket.Article;

/**
 * Base implementation for deals that apply to a specific article.
 */
public abstract class BaseDeal implements Deal {

    private final String articleNumber;

    /**
     * Creates a deal for the specified article number.
     *
     * @param articleNumber article number this deal applies to
     */
    protected BaseDeal(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    /**
     * Returns the article number this deal applies to.
     *
     * @return the applicable article number
     */
    @Override
    public String articleNumber() {
        return articleNumber;
    }

    /**
     * Calculates the deal price for the given article and quantity.
     *
     * @param article  article to price
     * @param quantity number of articles
     * @return price in cents
     * @throws IllegalArgumentException if the article does not match this deal
     */
    @Override
    public long priceFor(Article article, int quantity) {
        if (!articleNumber.equals(article.articleNumber())) {
            throw new IllegalArgumentException(
                    "Deal for " + articleNumber + " cannot be applied to " + article.articleNumber());
        }
        return calculate(article.priceInCents(), quantity);
    }

    /**
     * Calculates the total price for the supplied unit price and quantity.
     *
     * @param unitPriceInCents unit price in cents
     * @param quantity         number of articles
     * @return total price in cents
     */
    protected abstract long calculate(long unitPriceInCents, int quantity);

}
