package com.yelload.agro_news.exception.news;

public class NewsTitleNullException extends RuntimeException{

    public NewsTitleNullException() {
        super("News title can't be null");
    }
}
