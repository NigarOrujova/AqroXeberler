package com.yelload.agro_news.exception.image;

public class ImageTypeException extends RuntimeException{
    public ImageTypeException(String imageType) {
        super("Only accepted image type, you send: " + imageType);
    }
}
