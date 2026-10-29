package de.philipphertweck.shoppingbasket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.philipphertweck.shoppingbasket.deal.Deal;
import de.philipphertweck.shoppingbasket.exceptions.UnknownArticleException;

/**
 * A shopping basket that tracks scanned articles and calculates their total
 * price.
 */
public class Basket {

    private final Inventory inventory;
    private final Map<String, Integer> items = new HashMap<>();

    private final Map<String, Deal> deals = new HashMap<>();

    /**
     * Creates an empty basket backed by the given inventory.
     * 
     * @param inventory inventory used to look up scanned articles
     */
    public Basket(Inventory inventory) {
        this(inventory, List.of());
    }

    /**
     * Creates an empty basket backed by the given inventory and deals.
     *
     * @param inventory inventory used to look up scanned articles
     * @param deals     list of deals applicable to the basket
     */
    public Basket(Inventory inventory, List<Deal> deals) {
        this.inventory = inventory;

        for (Deal deal : deals) {
            if (this.deals.put(deal.articleNumber(), deal) != null) {
                throw new IllegalArgumentException(
                        "More than one deal for article " + deal.articleNumber());
            }
            this.deals.put(deal.articleNumber(), deal);
        }
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
                .mapToLong(entry -> priceFor(entry.getKey(), entry.getValue()))
                .sum();
    }

    /**
     * Calculates the price for a given article and quantity.
     *
     * @param articleNumber article number for which to calculate price
     * @param quantity      quantity of the article
     * @throws IllegalStateException if a scanned article is no longer in the
     *                               inventory
     * @return the price in cents
     */
    private long priceFor(String articleNumber, int quantity) {
        Article article = inventory.findArticle(articleNumber)
                .orElseThrow(() -> new IllegalStateException(
                        "Scanned article missing in inventory: " + articleNumber));
        Deal deal = deals.get(articleNumber);
        if (deal != null) {
            return deal.priceFor(article, quantity);
        }
        return article.priceInCents() * quantity;
    }
}
