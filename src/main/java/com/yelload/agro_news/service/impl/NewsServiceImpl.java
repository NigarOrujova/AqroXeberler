package com.yelload.agro_news.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yelload.agro_news.dto.ImageDto;
import com.yelload.agro_news.dto.NewsDto;
import com.yelload.agro_news.dto.TagDto;
import com.yelload.agro_news.dto.requests.NewsScrapingDataSaveDto;
import com.yelload.agro_news.dto.requests.NewsUpdateDto;
import com.yelload.agro_news.entity.Image;
import com.yelload.agro_news.entity.News;
import com.yelload.agro_news.entity.Tag;
import com.yelload.agro_news.exception.EntityNotFoundException;
import com.yelload.agro_news.exception.news.KeyWordNullException;
import com.yelload.agro_news.exception.news.NewsFieldstNullException;
import com.yelload.agro_news.repository.NewsRepository;
import com.yelload.agro_news.repository.TagRepository;
import com.yelload.agro_news.service.NewsService;
import lombok.NonNull;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final TagRepository tagRepository;
    private final ImageServiceImpl imageService;
    Logger log = LoggerFactory.getLogger(this.getClass());

    public NewsServiceImpl(NewsRepository newsRepository, TagRepository tagRepository, ImageServiceImpl imageService) {
        this.newsRepository = newsRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
    }

    static News unwrapNews(Optional<News> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, News.class);
    }

    static Tag unwrapTag(Optional<Tag> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, News.class);
    }

    @Override
    public NewsDto getNews(Long id) {
        Optional<News> dbNews = newsRepository.findById(id);
        News news = unwrapNews(dbNews, id);
        news.setView(news.getView() + 1);
        newsRepository.save(news);
        log.debug("Get news and convert to NewsDto");
        return convertEntityToDto(news);
    }

    @Override
    public Page<NewsDto> searchNewsByTitle(String keyWord, Pageable pageable) {
        log.debug("Check keyword is exist");
        if (keyWord.isEmpty() || keyWord == null) {
            throw new KeyWordNullException();
        }

        log.debug("Find news list by title");
        List<News> findNews = newsRepository.findAllByTitleContainingIgnoreCase(keyWord, pageable);

        log.debug("Convert news to dto");
        List<NewsDto> newsDtoList = findNews.stream().map(this::convertEntityToDto).collect(Collectors.toList());

        log.debug("Convert data to pageable format");
        return new PageImpl<>(newsDtoList, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), newsDtoList.size());
    }

    @Override
    public List<NewsDto> getAllNewsByTitle(String title) {
        log.debug("Get all news by title and convert to NewsDto");
        return newsRepository.findNewsByTitle(title)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public News saveNews(NewsDto newsDto) {
        News news = new News(
                newsDto.getTitle(),
                newsDto.getAuthor(),
                newsDto.getContent(),
                newsDto.getDate(),
                newsDto.getNewsSource(),
                false,
                false,
                false);
        log.debug("Create news entity with data");

        List<Image> images = new ArrayList<>();
        log.debug("Create image list");

        if (newsDto.getImages() != null) {
            newsDto.getImages().forEach(
                    imageDto -> {
                        images.add(new Image(imageDto.getId(), imageDto.getName(), imageDto.getLink(),
                                imageDto.getFileType(), imageDto.getImageData(), imageDto.getIsMainImage(), news));
                        log.debug("Add images to newsDto.getImages()");
                    });
        }

        news.setImages(images);
        newsRepository.save(news);
        log.debug("Save news");

        return news;
    }

    @Override
    public News saveNews(String newsString, MultipartFile file, MultipartFile[] files) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        NewsDto newsDto = mapper.readValue(newsString, NewsDto.class);
        log.debug("Convert dto to string");

        if (newsDto.getTitle() == null || newsDto.getContent() == null) {
            log.debug("News title or content is null");
            throw new NewsFieldstNullException();
        }
        log.debug("Check do news title and content are not null");

        List<Image> images = new ArrayList<>();
        log.debug("Create image list");

        images.add(imageService.multipartFileConvertToImage(file, true));

        if (files != null) {
            for (MultipartFile mFile : files) {
                if (mFile.isEmpty())
                    continue;
                images.add(imageService.multipartFileConvertToImage(mFile, false));
                log.debug("Save images and add to images list");
            }
        }


        News news = new News(
                newsDto.getTitle(),
                newsDto.getAuthor(),
                newsDto.getContent(),
                newsDto.getDate(),
                newsDto.getNewsSource(),
                images,
                new HashSet<>(),
                newsDto.getPublic(),
                newsDto.getMainNews(),
                newsDto.getTrendingNews());
        log.debug("Create news");

        newsRepository.save(news);
        log.debug("Save news");

        // images.forEach(img -> img.setNews(news));
        //
        //
        // imageRepository.saveAll(images);
        // log.debug("Images add news reference");
        imageService.setNewsToImages(news, images);

        return news;
    }

    @Override
    public void saveNewsFromScrapingData(NewsScrapingDataSaveDto newsDto) {
        News news = new News(
                newsDto.getTitle(),
                newsDto.getAuthor(),
                newsDto.getContent(),
                newsDto.getDate(),
                newsDto.getNewsSource(),
                false,
                false,
                false);

        List<Image> images = new ArrayList<>();

        if (newsDto.getImageDtoList() != null) {
            newsDto.getImageDtoList().forEach(
                    imageDto -> {
                        images.add(new Image(imageDto.getName(), imageDto.getLink(),
                                imageDto.getFileType(), imageDto.getMainImage(), news));
                    }
            );
        }

        news.setImages(images);
        newsRepository.save(news);
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
        log.debug("The news with id: {} deleted", id);
    }

    @Override
    public void deleteAllNews() {
        newsRepository.deleteAll();
        log.debug("All news deleted");
    }

    @Override
    public void deleteTagFromNews(Long tagId, Long newsId) {
        log.debug("Find news from db");
        Optional<News> newsOptional = newsRepository.findById(newsId);
        News dbNews = unwrapNews(newsOptional, newsId);

        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        Tag tag = unwrapTag(optionalTag, tagId);
        dbNews.getTags().remove(tag);

        newsRepository.save(dbNews);
    }

    @Override
    public List<NewsDto> getMainNews() {
        log.debug("Get all main news and convert to NewsDto");
        return newsRepository.findNewsByIsMainNewsTrue()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsDto> getTrendNews() {
        log.debug("Get all trend news and convert to NewsDto");
        return newsRepository.findNewsByIsTrendingNewsTrue()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsDto> getAllNewsBySize(Pageable pageable) {
        log.debug("Get all news with pageable");
        return newsRepository.findAll(pageable)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto addTagToNews(Long newsId, Long tagId) {
        Optional<News> dbNews = newsRepository.findById(newsId);
        News news = unwrapNews(dbNews, newsId);
        log.debug("Find news from db");

        Optional<Tag> dbTag = tagRepository.findById(tagId);
        Tag tag = TagServiceImpl.unwrapEntity(dbTag, tagId);
        log.debug("Find tag from db");

        news.getTags().add(tag);
        log.debug("Add tag to news");

        newsRepository.save(news);
        log.debug("Save news to db");
        return convertEntityToDto(news);
    }

    @Override
    public void addMainImageToNews(Long newsId, MultipartFile image) {
        Optional<News> dbNews = newsRepository.findById(newsId);
        News news = unwrapNews(dbNews, newsId);
        log.debug("Find news from db");

        Optional<Image> mainImage = news.getImages().stream().filter(Image::getIsMainImage).findFirst();
        mainImage.ifPresent(value -> imageService.deleteImage(value.getId()));

        Image newMainImage = imageService.saveImage(image);
        log.debug("Save new main image");

        imageService.setNewsToImage(news, newMainImage);
        log.debug("Set news to image");

        List<Image> images = new ArrayList<>();
        images.add(newMainImage);
        news.setImages(images);
        log.debug("Add main image to news");

        newsRepository.save(news);
        log.debug("Save news");

    }

    @Override
    public void addImagesToNews(Long newsId, MultipartFile[] images) {
        Optional<News> dbNews = newsRepository.findById(newsId);
        News news = unwrapNews(dbNews, newsId);
        log.debug("Find news from db");

        List<Image> newImages = imageService.saveImages(images);
        log.debug("Save new images");

        imageService.setNewsToImages(news, newImages);
        log.debug("Set news to image");

        news.setImages(newImages);
        log.debug("Add images to news");

        newsRepository.save(news);
        log.debug("Save news");
    }

    @Override
    public NewsUpdateDto updateNews(Long id, NewsUpdateDto newsUpdateDto) {
        Optional<News> dbNews = newsRepository.findById(id);
        News news = unwrapNews(dbNews, id);
        log.debug("Find News from db");

        if (Objects.nonNull(newsUpdateDto.getTitle()) && !"".equalsIgnoreCase(newsUpdateDto.getTitle())) {
            log.debug("Update news title field");
            news.setTitle(newsUpdateDto.getTitle());
        }
        if (Objects.nonNull(newsUpdateDto.getAuthor()) && !"".equalsIgnoreCase(newsUpdateDto.getAuthor())) {
            log.debug("Update news author field");
            news.setAuthor(newsUpdateDto.getAuthor());
        }
        if (Objects.nonNull(newsUpdateDto.getContent()) && !"".equalsIgnoreCase(newsUpdateDto.getContent())) {
            log.debug("Update news content field");
            news.setContent(newsUpdateDto.getContent());
        }
        if (Objects.nonNull(newsUpdateDto.getDate()) && !"".equalsIgnoreCase(newsUpdateDto.getDate())) {
            log.debug("Update news date field");
            news.setDate(newsUpdateDto.getDate());
        }
        if (Objects.nonNull(newsUpdateDto.getNewsSource()) && !"".equalsIgnoreCase(newsUpdateDto.getNewsSource())) {
            log.debug("Update news newsSource field");
            news.setNewsSource(newsUpdateDto.getNewsSource());
        }
        if (newsUpdateDto.getPublic() != news.getPublic()) {
            log.debug("Update news isPublic field");
            news.setPublic(newsUpdateDto.getPublic());
        }
        if (newsUpdateDto.getMainNews() != news.getMainNews()) {
            log.debug("Update news isMainNews field");
            news.setMainNews(newsUpdateDto.getMainNews());
        }
        if (newsUpdateDto.getTrendingNews() != news.getTrendingNews()) {
            log.debug("Update news isTrendingNews field");
            news.setTrendingNews(newsUpdateDto.getTrendingNews());
        }

        newsRepository.save(news);
        log.debug("Update news");
        return convertEntityToNewsUpdateDto(news);
    }

    protected NewsDto convertEntityToDto(@NonNull News news) {
        log.debug("Convert data from news to newsDto");

        List<ImageDto> imageDtoList = new ArrayList<>();
        if (news.getImages() != null) {
            news.getImages()
                    .forEach(image -> imageDtoList.add(new ImageDto(image.getId(), image.getName(), image.getLink(),
                            image.getFileType(), image.getIsMainImage())));
            log.debug("Add images to news.getImages()");
        }

        Set<TagDto> tagDtoSet = new LinkedHashSet<>();
        if (news.getTags() != null) {
            news.getTags().forEach(tag -> tagDtoSet.add(new TagDto(tag.getId(), tag.getName())));
            log.debug("Add tag to news.getTags()");
        }

        log.debug("Set images and tags");
        return new NewsDto(
                news.getNews_id(),
                news.getTitle(),
                news.getAuthor(),
                news.getContent(),
                news.getDate(),
                news.getNewsSource(),
                news.getView(),
                imageDtoList,
                tagDtoSet,
                news.getPublic(),
                news.getMainNews(),
                news.getTrendingNews());
    }

    private NewsUpdateDto convertEntityToNewsUpdateDto(News news) {
        log.debug("Convert data from news to newsUpdateDto ");

        List<ImageDto> imageDtoList = new ArrayList<>();
        news.getImages().forEach(image -> imageDtoList.add(new ImageDto(image.getId(), image.getName(), image.getLink(),
                image.getFileType(), image.getIsMainImage())));

        Set<TagDto> tagDtoSet = new LinkedHashSet<>();
        news.getTags().forEach(tag -> tagDtoSet.add(new TagDto(tag.getId(), tag.getName())));

        return new NewsUpdateDto(
                news.getTitle(),
                news.getAuthor(),
                news.getContent(),
                news.getDate(),
                news.getNewsSource(),
                news.getPublic(),
                news.getMainNews(),
                news.getTrendingNews(),
                imageDtoList,
                tagDtoSet);
    }

    @Override
    public void saveNewsFromExcel(MultipartFile file) throws IOException {
        FileInputStream fis = new FileInputStream(convertMultipartFileToFile(file));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        log.debug("Start take data from excel");

        // Skip the first row
        boolean isFirstRow = true;

        for (Row row : sheet) {
            // Skip the first row
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            // Create news
            String title =row.getCell(1) == null ? "no data" : row.getCell(1).getStringCellValue();
            String author = row.getCell(2) == null ? "no data" : row.getCell(2).getStringCellValue();
            String content = row.getCell(3) == null ? "no data" : row.getCell(3).getStringCellValue();
            String date = row.getCell(4) == null ? "no data" : row.getCell(4).getStringCellValue();
            String newsSource = row.getCell(5) == null ? "no data" : row.getCell(5).getStringCellValue();
            String mainImageUrl = row.getCell(6) == null ? "no data" : row.getCell(6).getStringCellValue();
            News news = new News(title, author, content, date, newsSource, false,false, false);
            Image image = new Image("excel_image", mainImageUrl, null, null, news, true);
            news.setImages(List.of(image));
            newsRepository.save(news);
            log.debug("Save news");
        }

        log.debug("Close file");
        workbook.close();
        fis.close();
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }



}
