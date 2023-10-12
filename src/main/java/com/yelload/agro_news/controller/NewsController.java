package com.yelload.agro_news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yelload.agro_news.dto.NewsDto;
import com.yelload.agro_news.dto.requests.NewsUpdateDto;
import com.yelload.agro_news.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNews(@PathVariable Long id) {
        log.debug("Get news by id => getNews()");
        return new ResponseEntity<>(newsService.getNews(id), HttpStatus.OK);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<HttpStatus> saveNews(@Validated @RequestBody NewsDto news) {
        log.debug("Save news => saveNews()");
        newsService.saveNews(news);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable Long id) {
        log.debug("Delete news by id: {} => deleteNews()", id);
        newsService.deleteNews(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @DeleteMapping("/all")
    public ResponseEntity<HttpStatus> deleteNewsAll() {
        log.debug("Delete all news => deleteNewsAll()");
        newsService.deleteAllNews();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @DeleteMapping("/{newsId}/delete/tag/{tagId}")
    public ResponseEntity<HttpStatus> deleteTagFromNews(@PathVariable Long newsId, @PathVariable Long tagId) {
        log.debug("Delete tag with {} id from news", tagId);
        newsService.deleteTagFromNews(tagId, newsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/main-news")
    public ResponseEntity<List<NewsDto>> getMainNews() {
        log.debug("Get main news => getMainNews()");
        return new ResponseEntity<>(newsService.getMainNews(), HttpStatus.OK);
    }

    @GetMapping("/trend-news")
    public ResponseEntity<List<NewsDto>> getTrendNews() {
        log.debug("Get trending news => getTrendNews()");
        return new ResponseEntity<>(newsService.getTrendNews(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsDto>> getNewsBySize(Pageable pageable) {
        log.debug("Get pageable news => getNewsBySize()");
        return new ResponseEntity<>(newsService.getAllNewsBySize(pageable), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{newsId}/tag/{tagId}")
    public ResponseEntity<NewsDto> addTagToNews(@PathVariable("newsId") Long newsId,
            @PathVariable("tagId") Long tagId) {
        log.debug("Add tag to news => addTagToNews()");
        return new ResponseEntity<>(newsService.addTagToNews(newsId, tagId), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/{newsId}/main-image")
    public ResponseEntity<HttpStatus> addMainImageToNews(@PathVariable("newsId") Long newsId,
            @RequestParam("file") MultipartFile file) {
        log.debug("Add MAIN image to news => addMainImageToNews()");
        newsService.addMainImageToNews(newsId, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/{newsId}/image")
    public ResponseEntity<HttpStatus> addImageToNews(@PathVariable("newsId") Long newsId,
            @RequestParam("files") MultipartFile[] files) {
        log.debug("Add MAIN image to news => addMainImageToNews()");
        newsService.addImagesToNews(newsId, files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<NewsUpdateDto> updateNews(@Valid @PathVariable("id") Long id,
            @RequestBody NewsUpdateDto newsUpdateDto) {
        log.debug("Update news => updateNews()");
        return new ResponseEntity<>(newsService.updateNews(id, newsUpdateDto), HttpStatus.ACCEPTED);
    }

    @Transactional
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveNewsWithImage(@Validated @RequestParam String news,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "files", required = false) MultipartFile[] files) throws JsonProcessingException {

        log.debug("Save news with image => saveNewsWithImage()");
        newsService.saveNews(news, file, files);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NewsDto>> searchNewsByTitle(Pageable pageable, @RequestParam("keyword") String keyword) {
        log.debug("Search news with {} keyword => searchNewsByTitle", keyword);
        return new ResponseEntity<>(newsService.searchNewsByTitle(keyword, pageable), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity saveNewsFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        newsService.saveNewsFromExcel(file);
        return ResponseEntity.ok("Done!");
    }

}
