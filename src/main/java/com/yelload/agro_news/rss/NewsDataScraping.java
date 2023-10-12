package com.yelload.agro_news.rss;

import com.yelload.agro_news.dto.requests.NewsScrapingDataSaveDto;
import com.yelload.agro_news.dto.requests.ScrapingDataImageSaveDto;
import com.yelload.agro_news.service.NewsService;
import com.yelload.agro_news.service.impl.ImageServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsDataScraping {
    private final NewsService newsService;
    private final NewsDataHelper newsDataHelper;
    Logger log = LoggerFactory.getLogger(this.getClass());

    public NewsDataScraping(NewsService newsService, NewsDataHelper newsDataHelper) {
        this.newsService = newsService;
        this.newsDataHelper = newsDataHelper;
    }

    public void getBetiAzNews(Document doc) {
        log.debug("The getBetiAzNews() method started");

        String newsSource = "Beti.az";
        Elements allPosts = doc.select("body .container .row .col-lg-4.col-md-4.col-sm-12.col-12.pb-4.pt-4");
        log.debug("ALl posts selected");


        for (Element post : allPosts) {
            try {
                log.debug("News data collecting...");
                doc = Jsoup.connect(post.child(0).attr("href")).get();
                Elements postContent = doc.select("body .container");
                String title = doc.select("body .container .row h3").text();
                String content = postContent.select("> div:eq(1) p").text();
                String mainImageUrl = post.select("a .card img").attr("src");
                Elements imagesList = postContent.select("> div:eq(2) div a img");
                log.debug("Image list selected");

                // Checking content length
                content = newsDataHelper.checkNewsContentLength(content);

                NewsScrapingDataSaveDto newsDto = new NewsScrapingDataSaveDto(
                        title, null, content, "No Date", newsSource, null
                );
                log.debug("NewsDto created");


                List<ScrapingDataImageSaveDto> images = new ArrayList<>();
                images.add(new ScrapingDataImageSaveDto("", mainImageUrl, "image", true, newsDto));
                log.debug("Main image added to image list");

                imagesList.forEach((img) -> {
                    if (!img.attr("src").isEmpty()) {
                        images.add(new ScrapingDataImageSaveDto(
                                "", img.attr("src"), "image", false, newsDto));
                    }
                });
                log.debug("Images added to image list");

                newsDto.setImageDtoList(images);
                newsService.saveNewsFromScrapingData(newsDto);
                log.debug("getBetiAzNews() - news saved");

            } catch (IOException e) {
                log.warn("An error occurred while opening the news detail link... Ex: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }

    }

    public void getBmtBetiAzNews(Document doc) {
        log.debug("The getBmtBetiAzNews() method started");

        String newsSource = "BmtBeti.az";
        Elements allPosts = doc.select("body .main-content #about").first().nextElementSibling().select(".container .row .col-md-16 div").first().select("div .item");
        log.debug("ALl posts selected");

        for (Element post : allPosts) {
            try {
                log.debug("News data collecting...");
                doc = Jsoup.connect(post.select(".entry-content h5 a").attr("href")).get();

                Elements postContent = doc.select("body .main-content section .container .row div div article");
                String title = postContent.select(".entry-meta .media-body .event-content h4 a").text();
                String content = postContent.select("div").text();
                String mainImageUrl = post.select(".item article .entry-header .post-thumb img").attr("src");
                Elements imagesList = postContent.select("div img");
                log.debug("Image list selected");

                // Checking content length
                content = newsDataHelper.checkNewsContentLength(content);

                NewsScrapingDataSaveDto newsDto = new NewsScrapingDataSaveDto(
                        title, null, content, "No Date", newsSource, null
                );
                log.debug("NewsDto created");

                List<ScrapingDataImageSaveDto> images = new ArrayList<>();
                images.add(new ScrapingDataImageSaveDto("", mainImageUrl, "image", true, newsDto));
                log.debug("Main image added to image list");

                imagesList.forEach((img) -> {
                    if (!img.attr("src").isEmpty()) {
                        images.add(new ScrapingDataImageSaveDto(
                                "", img.attr("src"), "image", false, newsDto));
                    }
                });
                log.debug("Images added to image list");

                newsDto.setImageDtoList(images);
                newsService.saveNewsFromScrapingData(newsDto);
                log.debug("getBmtBetiAzNews() - news saved");

            } catch (IOException e) {
                log.warn("An error occurred while opening the news detail link... Ex: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void getAeteiAzNews(Document doc) {
        log.debug("The getAeteiAzNews() method started");

        String newsSource = "Aetei.az";
        Elements allPosts = doc.select("body .main-body .container .row .col-sm-12 .blog .items-row .col-sm-3");
        log.debug("ALl posts selected");


        for (Element post : allPosts) {
            try {
                log.debug("News data collecting...");
                doc = Jsoup.connect("https://www.aetei.az" + post.select("a").attr("href")).get();

                Elements postContent = doc.select("body .main-body .container .row .col-sm-12 article .uk-grid div");
                String title = postContent.select("h2").text();
                String content = postContent.select("p").text().equals("") ? postContent.select("> div:not(:first-child)").text() : postContent.select("p").text();
                String date = post.select("article .published time").text();
                String mainImageUrl = "https://www.aetei.az" + post.select("article .imgcrop img").attr("src");
                log.debug("Image list selected");

                // Checking content length
                content = newsDataHelper.checkNewsContentLength(content);

                NewsScrapingDataSaveDto newsDto = new NewsScrapingDataSaveDto(
                        title, null, content, date, newsSource, null
                );
                log.debug("NewsDto created");

                List<ScrapingDataImageSaveDto> images = new ArrayList<>();
                images.add(new ScrapingDataImageSaveDto("", mainImageUrl, "image", true, newsDto));
                log.debug("Main image added to image list");

                newsDto.setImageDtoList(images);
                newsService.saveNewsFromScrapingData(newsDto);
                log.debug("getAeteiAzNews() - news saved");

            } catch (IOException e) {
                log.warn("An error occurred while opening the news detail link... Ex: {}", e.getMessage());
                throw new RuntimeException(e);
            }

        }
    }

    public void getWineGrapeGovAzNews(Document doc) {
        log.debug("The getWineGrapeGovAzNews() method started");

        String newsSource = "WineGrapeGovAz.az";
        Elements allPosts = doc.select("body #content .main-content .page-content .w-main .col-inner .w-blog-posts ul li");
        log.debug("ALl posts selected");

        for (Element post : allPosts) {
            log.debug("News data collecting...");
            try {
                doc = Jsoup.connect("http://wine-grape.gov.az" + post.select("article .post-content h3 a").attr("href")).get();
                Elements postContent = doc.select("body #content .main-content .page-content .w-main .col-inner .post-detail");
                String title = post.select("article .post-content h3 a").text();
                String content = postContent.select(".post-content p").text();
                String date = post.select(".post-meta span a").text();
                String mainImageUrl = "http://wine-grape.gov.az" + post.select(".post-media div a img").attr("src");
                Elements imagesList = postContent.select(".post-content p img");
                log.debug("Image list selected");

                // Checking content length
                content = newsDataHelper.checkNewsContentLength(content);

                NewsScrapingDataSaveDto newsDto = new NewsScrapingDataSaveDto(
                        title, null, content, date, newsSource, null
                );
                log.debug("NewsDto created");


                List<ScrapingDataImageSaveDto> images = new ArrayList<>();
                images.add(new ScrapingDataImageSaveDto("", mainImageUrl, "image", true, newsDto));
                log.debug("Main image added to image list");

                imagesList.forEach((img) -> {
                    if (!img.attr("src").isEmpty()) {
                        images.add(new ScrapingDataImageSaveDto(
                                "", img.attr("src"), "image", false, newsDto));
                    }
                });
                log.debug("Images added to image list");

                newsDto.setImageDtoList(images);
                newsService.saveNewsFromScrapingData(newsDto);
                log.debug("getWineGrapeGovAzNews() - news saved");

            } catch (IOException e) {
                log.warn("An error occurred while opening the news detail link... Ex: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }

    }

    public void getAkiaGovAzNews(Document doc) {
        log.debug("The getAkiaGovAzNews() method started");

        String newsSource = "Akia.gov.az";
        Elements allPosts = doc.select("body .body-wrapper .ltn__blog-area .container .row .col-lg-4");
        log.debug("ALl posts selected");

        for (Element post : allPosts) {
            log.debug("News data collecting...");
            try {
                doc = Jsoup.connect("http://akia.gov.az" + post.select(".ltn__blog-item .ltn__blog-brief h3 a").attr("href")).get();
                Elements postContent = doc.select("body .body-wrapper .ltn__product-area .container .row .col-lg-8 .ltn__blog-details-wrap .ltn__page-details-inner");
                String title = postContent.select("h2").text();
                String content = postContent.select("h5").text();
                String date = post.select(".ltn__blog-item .ltn__blog-brief .ltn__blog-meta-btn .ltn__blog-meta ul .ltn__blog-date").text();
                String mainImageUrl = "http://akia.gov.az" + postContent.select("div img").attr("src");
                log.debug("Image list selected");

                // Checking content length
                content = newsDataHelper.checkNewsContentLength(content);

                NewsScrapingDataSaveDto newsDto = new NewsScrapingDataSaveDto(
                        title, null, content, date, newsSource, null
                );
                log.debug("NewsDto created");

                List<ScrapingDataImageSaveDto> images = new ArrayList<>();
                images.add(new ScrapingDataImageSaveDto("", mainImageUrl, "image", true, newsDto));
                log.debug("Main image added to image list");

                newsDto.setImageDtoList(images);
                newsService.saveNewsFromScrapingData(newsDto);
                log.debug("getAkiaGovAzNews() - news saved");

            } catch (IOException e) {
                log.warn("An error occurred while opening the news detail link... Ex: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }


    }

    public void getAqroservisGovAzNews(Document doc) {
        log.debug("The getAqroservisGovAzNews() method started");

        String newsSource = "Aqroservis.gov.az";
        Elements allPosts = doc.select("body #page-top .main-body .container .row .col-sm-12 .blog .items-row .col-sm-3");
        log.debug("ALl posts selected");

        for (Element post : allPosts) {
            try {
                log.debug("News data collecting...");
                doc = Jsoup.connect("http://aqroservis.gov.az" + post.select("a").attr("href")).get();
                Elements postContent = doc.select("body #page-top .main-body .container .row .col-sm-12 article div .uk-grid .uk-width-large-3-4");
                String title = postContent.select("h2").text();
                String content = postContent.select("div").text().isEmpty() ?
                        postContent.select("p").text() : postContent.select("div").text();
                String date = post.select("a article .published time").text();
                String mainImageUrl = "http://aqroservis.gov.az" + post.select("a article p img").attr("src");
                Elements imagesList = postContent.select("#imageGallery").first().select("div");
                log.debug("Image list selected");

                // Checking content length
                content = newsDataHelper.checkNewsContentLength(content);

                NewsScrapingDataSaveDto newsDto = new NewsScrapingDataSaveDto(
                        title, null, content, date, newsSource, null
                );
                log.debug("NewsDto created");

                List<ScrapingDataImageSaveDto> images = new ArrayList<>();
                images.add(new ScrapingDataImageSaveDto("", mainImageUrl, "image", true, newsDto));
                log.debug("Main image added to image list");

                imagesList.forEach((img) -> {
                    if (!img.select("div img").attr("src").isEmpty()) {
                        System.out.println(img);
                        images.add(new ScrapingDataImageSaveDto(
                                "", "http://aqroservis.gov.az" + img.select("div img").attr("src"), "image", false, newsDto));
                    }
                });
                log.debug("Images added to image list");

                newsDto.setImageDtoList(images);
                newsService.saveNewsFromScrapingData(newsDto);
                log.debug("getBetiAzNews() - news saved");

            } catch (IOException e) {
                log.warn("An error occurred while opening the news detail link... Ex: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void getAgroTvAzerbaijanAzNews(Document doc) {
        log.debug("The getAgroTvAzerbaijanAzNews() method started");

        String newsSource = "AgroTvAzerbaijan.az";
        Elements allPosts = doc.select("body #services1-5 .container .row .col-lg-12 .row .blog-list");
        log.debug("ALl posts selected");

        for (Element post : allPosts) {
            try {
                log.debug("News data collecting...");
                doc = Jsoup.connect(post.select("a").attr("href")).get();
                Elements postContent = doc.select("body #services1-5 .container .row .col-lg-9 .blog-detail-content");
                String title = postContent.select("h1").text();
                String content = postContent.select(".blog-detail-page-text p").text();
                String date = post.select("a .text-left .date ").text();
                String mainImageUrl = postContent.select("img").attr("src");
                Elements imagesList = doc.select("body .product-description-review-area .container #home .row a");
                log.debug("Image list selected");

                // Checking content length
                content = newsDataHelper.checkNewsContentLength(content);

                NewsScrapingDataSaveDto newsDto = new NewsScrapingDataSaveDto(
                        title, null, content, date, newsSource, null
                );
                log.debug("NewsDto created");


                List<ScrapingDataImageSaveDto> images = new ArrayList<>();
                images.add(new ScrapingDataImageSaveDto("", mainImageUrl, "image", true, newsDto));
                log.debug("Main image added to image list");

                imagesList.forEach((img) -> {
                    if (!img.select("img").attr("src").isEmpty()) {
                        images.add(new ScrapingDataImageSaveDto(
                                "", img.select("img").attr("src"), "image", false, newsDto));
                    }
                });
                log.debug("Images added to image list");

                newsDto.setImageDtoList(images);
                newsService.saveNewsFromScrapingData(newsDto);
                log.debug("getAgroTvAzerbaijanAzNews() - news saved");
            } catch (IOException e) {
                log.warn("An error occurred while opening the news detail link... Ex: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }








//    public void getTetiAzNews(Document doc) {
//        log.debug("The getTetiAzNews() method started");
//
//        String newsSource = "Teti.az";
//        Elements allPosts = doc.select("body #SITE_CONTAINER #main_MF #site-root #masterPage #PAGES_CONTAINER #SITE_PAGES #SITE_PAGES_TRANSITION_GROUP #hl2xj .HT5ybB #Containerhl2xj div div #comp-jt8fxihz .E6jjcn .VM7gjN .Zc7IjY");
//        log.debug("ALl posts selected");
//
//        for (Element post : allPosts) {
//            try {
//                log.debug("News data collecting...");
//                doc = Jsoup.connect(post.select(".comp-jt8fxija .MW5IWV").next().select("div > div:eq(4) a").attr("href")).get();
//                Elements postContent = doc.select("body #SITE_CONTAINER #main_MF #site-root #masterPage #PAGES_CONTAINER #SITE_PAGES #SITE_PAGES_TRANSITION_GROUP .HT5ybB ");
////                String title = doc.select("body .container .row h3").text();
////                String content = postContent.select("> div:eq(1) p").text();
////                String mainImageUrl = post.select("a .card img").attr("src");
////                Elements imagesList = postContent.select("> div:eq(2) div a img");
////                log.debug("Image list selected");
//
//                System.out.println(postContent);
//                break;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//

}
