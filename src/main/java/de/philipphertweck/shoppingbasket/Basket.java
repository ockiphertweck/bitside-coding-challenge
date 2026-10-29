package de.philipphertweck.shoppingbasket;

import java.util.HashMap;
import java.util.Map;

import de.philipphertweck.shoppingbasket.exceptions.UnknownArticleException;

/**
 * A shopping basket that tracks scanned articles and calculates their total
 * price.
 */
public class Basket {

    private final Inventory inventory;
    private final Map<String, Integer> items = new HashMap<>();

    /**
     * Creates an empty basket backed by the given inventory.
     *
     * @param inventory inventory used to look up scanned articles
     */
    public Basket(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Adds one unit of an article to the basket.
     *
     * @param articleNumber article number to scan
     * @throws UnknownArticleException if the inventory does not contain the article
     */
    public void scan(String articleNumber) {
        Article article = inventory.findArticle(articleNumber)
                .orElseThrow(() -> new UnknownArticleException(articleNumber));
        items.merge(article.articleNumber(), 1, Integer::sum);
    }

    /**
     * Calculates the total price of all items in cents.
     *
     * @return the basket total in cents
     * @throws IllegalStateException if a scanned article is no longer in the
     *                               inventory
     */
    public long total() {
        return items.entrySet().stream()
                .mapToLong(entry -> inventory.findArticle(entry.getKey())
                        .orElseThrow(() -> new IllegalStateException(
                                "Scanned article missing in inventory: " + entry.getKey()))
                        .priceInCents() * entry.getValue())
                .sum();
    }

}
