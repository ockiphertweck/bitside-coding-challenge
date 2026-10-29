package de.philipphertweck.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ArticleTest {

    @Test
    void createArticleSuccessfully() {
        Article article = new Article("123", 100);
        assertEquals("123", article.articleNumber());
        assertEquals(100, article.priceInCents());
    }

    @Test
    void rejectBlankArticleNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Article("", 100));
    }

    @Test
    void rejectNullArticleNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Article(null, 100));
    }

    @Test
    void rejectNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Article("123", -1));
    }

    @Test
    void acceptZeroPrice() {
        Article article = new Article("123", 0);
        assertEquals(0, article.priceInCents());
    }
}
