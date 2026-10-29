package de.philipphertweck.shoppingbasket;

public record Article(String articleNumber, long priceInCents) {
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
