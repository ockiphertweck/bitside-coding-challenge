import de.philipphertweck.shoppingbasket.Article;

/**
 * A sales deal that applies to one article.
 */
public interface Deal {

    /**
     * @return the number of the article this deal applies to
     */
    String articleNumber();

    /**
     * Calculates the price for a quantity of the given article with this deal
     * applied.
     *
     * @param article  the article, its number must match {@link #articleNumber()}
     * @param quantity how many items of the article were scanned
     * @return the total price in cents for all items
     * @throws IllegalArgumentException if the article does not match this deal
     */
    long priceFor(Article article, int quantity);
}