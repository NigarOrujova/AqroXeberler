package com.yelload.agro_news.service.impl;

import com.yelload.agro_news.dto.NewsDto;
import com.yelload.agro_news.dto.TagDto;
import com.yelload.agro_news.entity.Tag;
import com.yelload.agro_news.exception.EntityNotFoundException;
import com.yelload.agro_news.repository.TagRepository;
import com.yelload.agro_news.service.TagService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final NewsServiceImpl newsService;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper, NewsServiceImpl newsService) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
        this.newsService = newsService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public TagDto getTag(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        log.debug("Get tag and convert tagDto");
        return convertEntityToDto(unwrapEntity(tag, id));
    }

    @Override
    public Tag saveTag(TagDto tagDto) {
        Tag newTagDtp = modelMapper.map(tagDto, Tag.class);
        log.debug("Save tag");
        return tagRepository.save(newTagDtp);
    }

    @Override
    public Tag updateTag(Long id, TagDto tagDto) {
        Optional<Tag> dbTag = tagRepository.findById(id);
        Tag tag = unwrapEntity(dbTag, id);
        log.debug("Find tag from db");

        if (Objects.nonNull(tagDto.getName()) && !"".equalsIgnoreCase(tagDto.getName())) {
            log.debug("Update tag's name field");
            tag.setName(tagDto.getName());
        }
        tagRepository.save(tag);
        log.debug("Update tag");
        return tag;
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
        log.debug("The tag with id: {} deleted", id);
    }

    @Override
    public List<TagDto> getAllTags() {
        log.debug("Get all tags");
        return tagRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TagDto> getAllTags(Pageable pageable) {
        List<TagDto> tagDtoList = tagRepository.findAll(pageable)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        return tagDtoList;
    }

    @Override
    public List<NewsDto> getNewsByTagId(Long id) {
        Optional<Tag> dbTag = tagRepository.findById(id);
        Tag tag = unwrapEntity(dbTag, id);
        log.debug("Find tag from db");

        List<NewsDto> newsDtos = new ArrayList<>();
        tag.getNews().forEach((news -> {
            newsDtos.add(newsService.convertEntityToDto(news));
            log.debug("Add news to tag.getNews()");
        }));
        return newsDtos;
    }

    static Tag unwrapEntity(Optional<Tag> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        throw new EntityNotFoundException(id, Tag.class);
    }

    protected TagDto convertEntityToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());

        List<NewsDto> newsList = new ArrayList<>();
        if (tag.getNews() != null) {
            tag.getNews().forEach((news -> {
                newsList.add(new NewsDto(
                        news.getNews_id(),
                        news.getTitle(),
                        news.getAuthor(),
                        news.getContent(),
                        news.getDate(),
                        news.getNewsSource()));
            }));
        }
        tagDto.setNewsDtos(newsList);

        log.debug("Convert Tag to TagDto");
        return tagDto;
    }
}
