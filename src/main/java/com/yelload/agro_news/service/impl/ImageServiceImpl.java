package com.yelload.agro_news.service.impl;

import com.yelload.agro_news.dto.ImageDto;
import com.yelload.agro_news.entity.Image;
import com.yelload.agro_news.entity.News;
import com.yelload.agro_news.exception.EntityNotFoundException;
import com.yelload.agro_news.exception.image.ImageSizeException;
import com.yelload.agro_news.exception.image.ImageTypeException;
import com.yelload.agro_news.repository.ImageRepository;
import com.yelload.agro_news.repository.NewsRepository;
import com.yelload.agro_news.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final NewsRepository newsRepository;
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.profiles.active}")
    private String env;

    public ImageServiceImpl(ImageRepository imageRepository, NewsRepository newsRepository) {
        this.imageRepository = imageRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public List<ImageDto> getAllImages(Pageable pageable) {
        return imageRepository.findAll(pageable)
                .stream()
                .map(this::convertImageToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageDto> getAllImage() {
        log.debug("Get all images and convert to ImageDto");
        return imageRepository.findAll()
                .stream()
                .map(this::convertImageToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDto getImage(String id) {
        log.debug("Find image by id");
        Image image = imageRepository.findById(id)
                .orElseThrow(
                        () -> {
                            log.debug("Image with id: {} not found", id);
                            return new EntityNotFoundException(Image.class);
                        });
        return convertImageToDto(image);
    }

    @Override
    public Image saveImage(MultipartFile file) {
        log.debug("Save image to database");
        return saveImageFunction(file, true);
    }

    @Override
    public void deleteImage(String id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Image not found with ID: " + id));

        News news = image.getNews();
        if (news != null) {
            news.getImages().remove(image);
            newsRepository.save(news);
        }


        log.debug("The image with id: {} deleted", id);
        imageRepository.deleteById(id);
    }

    @Override
    public List<Image> saveImages(MultipartFile[] files) {
        List<Image> images = new ArrayList<>();
        log.debug("Create images list");
        for (MultipartFile file : files) {
            images.add(saveImageFunction(file, false));
            log.debug("Add image to images list");
        }

        return images;
    }

    private ImageDto convertImageToDto(Image image) {
        return new ImageDto(
                image.getId(),
                image.getName(),
                image.getLink(),
                image.getFileType(),
                image.getImageData(),
                image.getIsMainImage()
        );
    }

    private Image saveImageFunction(MultipartFile file, Boolean isMainImage) {
        if (file.getSize() > 5242880) { // 5Mb
            log.debug("Image size more than 5MB. Image size: {}", file.getSize());
            throw new ImageSizeException(file.getOriginalFilename());
        }
        if (!Objects.requireNonNull(file.getContentType()).contains("image")) {
            log.debug("FIle type is not image. File type: {}", file.getContentType());
            throw new ImageTypeException(file.getContentType());
        }

        Image image;
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            image = new Image(
                    fileName,
                    "link",
                    file.getContentType(),
                    file.getBytes(),
                    null,
                    isMainImage
            );
            imageRepository.save(image);

            String link = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/image/find/")
                    .path(image.getId())
                    .toUriString();

            image.setLink(link);
            imageRepository.save(image);
            log.debug("Image saved");
        } catch (IOException e) {
            log.debug("Image can't uploaded...");
            throw new RuntimeException("Image can't uploaded...");
        }

        return image;
    }

    protected Image multipartFileConvertToImage(MultipartFile file, Boolean isMainImage) {
        if (file.getSize() > 5242880) { // 5Mb
            log.debug("Image size more than 5MB. Image size: {}", file.getSize());
            throw new ImageSizeException(file.getOriginalFilename());
        }
        if (!Objects.requireNonNull(file.getContentType()).contains("image")) {
            log.debug("FIle type is not image. File type: {}", file.getContentType());
            throw new ImageTypeException(file.getContentType());
        }

        Image image;
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            image = new Image(
                    fileName,
                    "link",
                    file.getContentType(),
                    file.getBytes(),
                    null,
                    isMainImage
            );
            imageRepository.save(image);

            String link;
            if (env.equals("prod")) {
                link = "http://164.90.216.81:8081/image/find/" + image.getId();
            } else {
                link = "http://localhost:8080/image/find/" + image.getId();
            }

            image.setLink(link);
            imageRepository.save(image);
        } catch (IOException e) {
            log.debug("Image can't uploaded...");
            throw new RuntimeException("Image can't uploaded...");
        }

        return image;
    }

    protected void setNewsToImage(News news, Image image) {
        image.setNews(news);
        imageRepository.save(image);
    }

    protected void setNewsToImages(News news, List<Image> images) {
        images.forEach(image -> image.setNews(news));
        imageRepository.saveAll(images);
    }


}
