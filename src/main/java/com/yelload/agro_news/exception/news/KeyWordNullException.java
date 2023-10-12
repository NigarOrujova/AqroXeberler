package com.yelload.agro_news.exception.news;

public class KeyWordNullException extends RuntimeException{
    public KeyWordNullException() {
        super("Keyword can't be null");
    }
}
