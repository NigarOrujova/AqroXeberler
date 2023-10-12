package com.yelload.agro_news.exception.image;

public class ImageSizeException extends RuntimeException{
    public ImageSizeException(String imageName) {
        super(imageName + " size can't be more than 5MB");
    }
}
