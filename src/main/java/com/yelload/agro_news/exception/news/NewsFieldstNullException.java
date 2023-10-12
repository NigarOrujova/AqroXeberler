package com.yelload.agro_news.exception.news;

public class NewsFieldstNullException extends RuntimeException{
    public NewsFieldstNullException() {
        super("News title and content can't be null");
    }
}
