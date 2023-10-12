package com.yelload.agro_news.controller;

import com.yelload.agro_news.dto.NewsDto;
import com.yelload.agro_news.dto.TagDto;
import com.yelload.agro_news.entity.Tag;
import com.yelload.agro_news.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTag(@PathVariable("id") Long id) {
        log.debug("Get tag by id");
        return new ResponseEntity<>(tagService.getTag(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveTag(@Valid @RequestBody TagDto tagDto) {
        tagService.saveTag(tagDto);
        log.debug("Save tag => saveTag()");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @Valid @RequestBody TagDto tagDto) {
        log.debug("Update tag => updateTag()");
        return new ResponseEntity<>(tagService.updateTag(id, tagDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
        log.debug("Delete tag by id: {} => deleteTag()", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/news")
    public ResponseEntity<List<NewsDto>> getNewsByTagId(@PathVariable Long id) {
        log.debug("Get news by tag id => getNewsByTagId()");
        return new ResponseEntity<>(tagService.getNewsByTagId(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagDto>> findAllTagsBySize(Pageable pageable) {
        return new ResponseEntity<>(tagService.getAllTags(pageable), HttpStatus.OK);
    }

}
