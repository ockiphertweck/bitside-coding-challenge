package de.philipphertweck.shoppingbasket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Inventory {

    private final Map<String, Article> articles = new HashMap<>();

    public void addArticle(Article article) {
        if (articles.containsKey(article.articleNumber())) {
            throw new IllegalArgumentException("Article with number " + article.articleNumber() + " already exists");
        }
        articles.put(article.articleNumber(), article);
    }

    public Optional<Article> findArticle(String articleNumber) {
        return Optional.ofNullable(articles.get(articleNumber));
    }

}
