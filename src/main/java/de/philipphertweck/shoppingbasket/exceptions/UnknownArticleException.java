package de.philipphertweck.shoppingbasket.exceptions;

public class UnknownArticleException extends RuntimeException {

    public UnknownArticleException(String articleNumber) {
        super("Article with number " + articleNumber + " does not exist");
    }
}