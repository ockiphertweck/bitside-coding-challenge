package de.philipphertweck.shoppingbasket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Stores articles available in the inventory, indexed by article number.
 */
public class Inventory {

    private final Map<String, Article> articles = new HashMap<>();

    /**
     * Adds an article to the inventory.
     *
     * @param article the article to add
     * @throws IllegalArgumentException if an article with the same number already
     *                                  exists
     */
    public void addArticle(Article article) {
        if (articles.containsKey(article.articleNumber())) {
            throw new IllegalArgumentException("Article with number " + article.articleNumber() + " already exists");
        }
        articles.put(article.articleNumber(), article);
    }

    /**
     * Finds an article by its article number.
     *
     * @param articleNumber the number of the article to find
     * @return the article, or an empty optional if no matching article exists
     */
    public Optional<Article> findArticle(String articleNumber) {
        return Optional.ofNullable(articles.get(articleNumber));
    }

}
