package com.yelload.agro_news.controller;

import com.yelload.agro_news.dto.ImageDto;
import com.yelload.agro_news.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getImage(@PathVariable("id") String id) throws Exception {
        log.debug("Get image by id => getImage()");
        return new ResponseEntity<>(imageService.getImage(id), HttpStatus.OK);
    }

    @PostMapping("/save/main/image")
    public ResponseEntity<HttpStatus> saveMainImage(@RequestParam("file") MultipartFile file) {
        imageService.saveImage(file);
        log.debug("Save main image => saveMainImage()");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/save/all/image")
    public ResponseEntity<List<HttpStatus>> saveImage(@RequestParam("files") MultipartFile[] files) {
        imageService.saveImages(files);
        log.debug("Save all other images => saveImage()");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable("id") String id) {
        imageService.deleteImage(id);
        log.debug("Delete image by id: {} => deleteImage()", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Resource> showFile(@PathVariable("id") String fileId) throws Exception {
        ImageDto image = imageService.getImage(fileId);
        log.debug("Get only image by id => showFile()");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + image.getName() + "\"")
                .body(new ByteArrayResource(image.getImageData()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ImageDto>> getAllImagesBySize(Pageable pageable) {
        log.debug("Get pageable images => getAllImagesBySize()");
        return new ResponseEntity<>(imageService.getAllImages(pageable), HttpStatus.OK);
    }
}
