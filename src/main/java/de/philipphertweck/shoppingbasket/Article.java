package de.philipphertweck.shoppingbasket;

/**
 * An article that can be added to an inventory and can be scanned into a
 * basket.
 *
 * @param articleNumber the non-blank article number
 * @param priceInCents  the non-negative price in cents
 */
public record Article(String articleNumber, long priceInCents) {
    /**
     * Creates an article after validating its number and price.
     *
     * @param articleNumber the non-blank article number
     * @param priceInCents  the non-negative price in cents
     * @throws IllegalArgumentException if the number is null or blank, or the price
     *                                  is negative
     */
    public Article(String articleNumber, long priceInCents) {
        if (articleNumber == null || articleNumber.isBlank()) {
            throw new IllegalArgumentException("Article number must not be null or blank");
        }
        if (priceInCents < 0) {
            throw new IllegalArgumentException("Price in cents must not be negative");
        }
        this.articleNumber = articleNumber;
        this.priceInCents = priceInCents;
    }

}
