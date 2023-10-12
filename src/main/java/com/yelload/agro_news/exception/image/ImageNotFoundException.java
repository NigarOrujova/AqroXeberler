package com.yelload.agro_news.exception.image;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(String id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }
}
