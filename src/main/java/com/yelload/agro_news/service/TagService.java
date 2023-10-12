package com.yelload.agro_news.service;

import com.yelload.agro_news.dto.NewsDto;
import com.yelload.agro_news.dto.TagDto;
import com.yelload.agro_news.entity.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    TagDto getTag(Long id);

    Tag saveTag(TagDto tagDto);

    Tag updateTag(Long id, TagDto tagDto);

    void deleteTag(Long id);

    List<TagDto> getAllTags();

    List<TagDto> getAllTags(Pageable pageable);

    List<NewsDto> getNewsByTagId(Long id);
}
