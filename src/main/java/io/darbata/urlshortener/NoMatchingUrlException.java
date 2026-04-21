package io.darbata.urlshortener;

public class NoMatchingUrlException extends RuntimeException {
    public NoMatchingUrlException(String message) {
        super(message);
    }
}