package com.yelload.agro_news.service;

import com.yelload.agro_news.dto.ImageDto;
import com.yelload.agro_news.entity.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageDto> getAllImages(Pageable pageable);

    List<ImageDto> getAllImage();

    ImageDto getImage(String id) throws Exception;

    Image saveImage(MultipartFile files);

    void deleteImage(String id);

    List<Image> saveImages(MultipartFile[] files);
}
