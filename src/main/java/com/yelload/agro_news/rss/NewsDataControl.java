package com.yelload.agro_news.rss;

import com.yelload.agro_news.dto.ImageDto;
import com.yelload.agro_news.dto.NewsDto;
import com.yelload.agro_news.service.NewsService;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class NewsDataControl {
    private final NewsService newsService;
    private final NewsDataHelper changeDate;
    public NewsDataControl(NewsService newsService, NewsDataHelper changeDate) {
        this.newsService = newsService;
        this.changeDate = changeDate;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());


    public void getAgroGovAzNews(Document doc) throws IOException {
        String newsSource = "Agro.gov.az";
        Elements allPosts = doc.select(".container .overlay-news-list-cards div");
        Elements mainNews = doc.select(".container .overlay-main-news div a");

        for (var post : mainNews) {
            doc = Jsoup.connect(post.select(".main-news-card").attr("href")).get();
            Elements title = doc.select(".container .overlay--news .news---card-title");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements content = doc.select(".text-zoom-js .active--zoom p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());


            Elements date = post.select(".news-list-card-data");
            String mainImage = "https://agro.gov.az" + doc.select(".container .overlay--news img").attr("src");
            String newDate = changeDate.getStandardDateAndChangeMonthFormat(date.text());
            Elements imagesList = doc.select(".container .overlay--news .text-zoom-js .active--zoom .page-gallery a");

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getAgroGovAzNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            imagesList.forEach((img) -> {
                if (!img.select("img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            "https://agro.gov.az" + img.select("img").attr("src"),
                            "image", null, false, newsDto));
                    System.out.println("https://agro.gov.az" + img.select("img").attr("src"));

                    URL url = null;
                    try {
                        url = new URL("https://agro.gov.az" + img.select("img").attr("src"));
                        InputStream is = url.openStream();

                        String newUrl = "C:\\Users\\nigarorucova\\Desktop\\Aqro\\aqroxeber\\src\\main\\java\\com\\yelload\\agro_news\\images\\" + "image" + System.currentTimeMillis() + ".jpg";
                        System.out.println(newUrl);
                        OutputStream os = new FileOutputStream(newUrl);

                        byte[] b = new byte[2048];
                        int length;

                        while ((length = is.read(b)) != -1) {
                            os.write(b, 0, length);
                        }

                        is.close();
                        os.close();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


//                    try {
//                        URL url = new URL("https://agro.gov.az" + img.select("img").attr("src"));
//                        URLConnection connection = null;
//                        connection = url.openConnection();
//                        connection.setRequestProperty("User-Agent", "xxxxxx");
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }



//                    try(InputStream in = new URL("https://agro.gov.az" + img.select("img").attr("src")).openStream()){
//
//
////                        FileOutputStream fileOutputStream = new FileOutputStream("https://agro.gov.az" + img.select("img").attr("src"));
////                        fileOutputStream.write();
////                        Files.copy(in, Paths.get("C:\\Users\\nigarorucova\\Desktop\\Aqro\\aqroxeber\\src\\main\\java\\com\\yelload\\agro_news\\Images"));
//
//                    } catch (MalformedURLException e) {
//                        throw new RuntimeException(e);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                    log.debug("getAgroGovAzNews() - image added to image list");
                }
            });

            newsDto.setImages(images);
            log.debug("getAgroGovAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getAgroGovAzNews() - main news saved");

        }
        for (var post : allPosts) {
            doc = Jsoup.connect(post.select(".news-list-card").attr("href")).get();
            Elements title = doc.select(".container .overlay--news .news---card-title");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements content = doc.select(".text-zoom-js .active--zoom p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            Elements date = post.select(".news-list-card-data");
            String mainImage = "https://agro.gov.az" + doc.select(".container .overlay--news img").attr("src");
            String newDate = changeDate.getStandardDateAndChangeMonthFormat(date.text());
            Elements imagesList = doc.select(".container .overlay--news .text-zoom-js .active--zoom .page-gallery a");

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getAgroGovAzNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            imagesList.forEach((img) -> {
                if (!img.select("img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            "https://agro.gov.az" + img.select("img").attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getAgroGovAzNews() - image added to image list");
                }
            });
            newsDto.setImages(images);
            log.debug("getAgroGovAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getAgroGovAzNews() - main news saved");
        }
    }
    public void getAtmGovAzNews(Document doc) throws IOException {
        Elements allPosts = doc.select("#blog .container .row .news-list-warp .col-md-6");
        String newsSource = "Atm.gov.az";

        for (var post : allPosts) {
            doc = Jsoup
                    .connect("https://atm.gov.az/" + post.select(".item-new-list .feature-new-warp a").attr("href"))
                    .get();
            Elements postContent = doc.select(".container .row .col-md-8 .services-2-detail-warp .new-detail-warp");

            Elements title = postContent.select(".new-info.new-info-detail h1");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements content = postContent.select(".content-detail.content-new-detail p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            Elements date = postContent.select(".new-info.new-info-detail p");
            String newDate = changeDate.getStandardDateAndChangeMonthFormat(date.text());
            String mainImage = "https://atm.gov.az/" + postContent.select(".feature-img-deital img").attr("src");
            Elements imagesList = doc.select(".content-detail.content-new-detail .row");

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getAgroGovAzNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));
            imagesList.forEach((img) -> {
                if (!img.select(".col-md-6 a img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            "https://atm.gov.az/" + img.select(".col-md-6 a img").attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getAtmGovAzNews() - image added to image list");
                }
            });
            newsDto.setImages(images);
            log.debug("getAgroGovAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getAgroGovAzNews() - main news saved");
        }
    }
    public void getAdauGovAzNews(Document doc) throws IOException {
        Elements allPosts = doc.select(".content-area .row .site-main .article-content-area #ms-content .col-md-6");
        String newsSource = "Adau.edu.az";

        for (var post : allPosts) {
            doc = Jsoup.connect(post.select(".entry-header h5 a").attr("href")).get();
            Elements postContent = doc.select("#page #ms-content .site-content .row #primary .site-main");
            Elements title = postContent.select(".post-details h4");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements content = postContent.select(".entry-content .post-image .post_featured span");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            String mainImage = "http://www.adau.edu.az" + post.select(".post-image a img").attr("src");

            String newDate;

            if (post.select(".entry-meta").first() != null){
                Element date = Objects.requireNonNull(post.select(".entry-meta").first()).child(0);
                newDate = changeDate.getStandardDateAndChangeDot(date.text());
            } else {
                newDate = "Empty Date";
            }

            Elements imagesList = doc.select(".entry-content .post-image .post_featured a");

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getAdauGovAzNews - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            imagesList.forEach((img) -> {
                if (!img.select("img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            "http://www.adau.edu.az" + img.select("img").attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getAdauGovAzNews() - image added to image list");
                }
            });

            newsDto.setImages(images);
            log.debug("getAdauGovAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getAdauGovAzNews() - main news saved");
        }
    }
    public void getAzertacNews(@NonNull Document doc) throws IOException {
        Elements allPosts = doc.select("#site-global #main #content-out #content-article " +
                ".main-content .main-content-inner .inner-tabnews.inner-tabnews-list.agriculture" +
                " .inner-tabnews-inner .content .content-inner .news-item ");
        String newsSource = "Azertag.az";

        for (var post : allPosts) {
            doc = Jsoup.connect("https://azertag.az/" + post.select(".news-title a").attr("href")).get();

            Elements title = post.select(".news-title a span");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements content = doc.select("#site-global #main #content-out #content #content-article " +
                    ".main-content .main-content-inner span .content-outtext .left-content #selectedtext p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            Elements date = post.select(".news-date");
            String newDate = changeDate.replaceDotWithSlash(changeDate.splitDate(date.text()));
            String mainImage = "https://azertag.az" + doc.select("#site-global #main #content-out #content #content-article " +
                    ".main-content .main-content-inner span .content-outtext .left-content img").attr("src");

            Elements imagesList = doc.select("#site-global #main #content-out #content #content-article " +
                    ".main-content .main-content-inner span .content-text .content-gallery .galleria img");


            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getAzertacNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));
            imagesList.forEach((img) -> {
                if (img.attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            "https://azertag.az/" + img.attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getAzertacNews() - image added to image list");
                }
            });
            newsDto.setImages(images);
            log.debug("getAzertacNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getAzertacNews() - main news saved");
        }
    }
    public void getAgrovolunteersNews(@NonNull Document doc) throws IOException {
        Elements allPosts = doc.select(".page-builder .container .row .col-md-8 .blog-lg-area-left" +
                " .pt-cv-wrapper .pt-cv-view.pt-cv-grid .pt-cv-page .col-md-12");
        String newsSource = "Agrovolunteers.az";

        for (var post : allPosts) {
            doc = Jsoup.connect(post.select(".pt-cv-ifield h4 a").attr("href")).get();
            Elements postContent = doc.select(".page-builder .container .row .col-md-8 .media .media-body");
            Elements title = postContent.select("h3 a");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements content = postContent.select("p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            String mainImage = postContent.select(".wp-block-image img").attr("src");
            String newDate = "No Date";

            Elements imagesList = postContent.select(".wp-block-image");
            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getAgrovolunteersNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));
            imagesList.forEach((img) -> {
                if (!img.select("img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            img.select("img").attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getAgrovolunteersNews() - image added to image list");
                }
            });
            newsDto.setImages(images);
            log.debug("getAgrovolunteersNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getAgrovolunteersNews() - main news saved");
        }
    }
    public void getAxaGovAzNews(Document doc) throws IOException {
        Elements allPosts = doc.select("#announcement-area .container .row .col-12.col-md-4 ");
        String newsSource = "Axa.gov.az";

        for (var post : allPosts) {
            doc = Jsoup.connect(post.select(".card .cart-writing-up .root-area .right a").attr("href")).get();

            Elements title = post.select(".card .cart-writing-up .description");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements content = doc.select("#writing-side .container .row .col-12 .row.for-writing p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            Elements date = post.select(".card .cart-writing-up .root-area .left");
            String newDate = changeDate.getStandardDateForAxaGovAz(date.text());
            String mainImage = doc.select("#writing-side .container .row .col-12 .for-title img").attr("src");

            Elements imagesList = doc.select("#writing-side .container .row #lightgallery a");

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getAxaGovAzNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            imagesList.forEach((img) -> {
                    if (!img.select(".col-md-3 img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            img.select(".col-md-3 img").attr("src"),
                            "image", null, false, newsDto));
                        log.debug("getAxaGovAzNews() - image added to image list");
                }
            });
            newsDto.setImages(images);
            log.debug("getAxaGovAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getAxaGovAzNews() - main news saved");
        }
    }
    public void getApaAzNews(Document doc) throws IOException {
        Elements allPosts = doc.select("main .container .four_columns_block .item");
        String newsSource = "Apa.az";

        for (var post : allPosts) {
            doc = Jsoup.connect(post.attr("href")).get();
            Elements title = post.select(".content .title");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements postContent = doc.select("main .container #main-content .content .news_main");

            Elements content = postContent.select(".news_content .texts p span span");
            Elements content2 = postContent.select(".news_content .texts p span span span span");
            Elements content3 = postContent.select(".news_content .texts p");
            Elements date = postContent.select(".date_news .date div .date");


            String newDate = changeDate.getStandardDateForApaAz(date.text());

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(changeDate.chooseContent(content, content2, content3));

            String mainImage = postContent.select(".main_img img").attr("src");

            Elements imagesList = postContent.select(".news_content .texts p img");
            Elements imagesList2 = postContent.select(".news_content .tabs_container #photos div a");

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getApaAzNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            imagesList.forEach((img) -> {
                if (img.attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            img.attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getApaAzNews() - image added to image list");
                }
            });

            imagesList2.forEach((img) -> {
                if (!img.select("img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            img.select("img").attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getApaAzNews() - image added to image list");
                }
            });


            newsDto.setImages(images);
            log.debug("getApaAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getApaAzNews() - main news saved");
        }
    }
    public void getEUNeighboursEastNews(Document doc) throws IOException {
        Elements allPosts = doc.select(".tm-page .uk-section-default .tm-grid-expand.uk-grid-margin " +
                "#main-filtered-content .uk-grid-match div");
        String newsSource = "EUNeighboursEast.az";

        for (var post : allPosts) {
            if (!post.select("a").attr("href").isEmpty()) {
                doc = Jsoup.connect(post.select("a").attr("href")).get();
                Elements title = doc.select(".uk-section-default .uk-container .uk-grid-margin .tm-grid-expand h1");
                if (changeDate.newsExist(title.text(), newsSource)) {
                    break;
                }
                Elements content = doc
                        .select(".uk-section-default .uk-container .uk-grid-margin .tm-grid-expand .uk-panel p");

                // Check content length
                String newShortContent = changeDate.checkNewsContentLength(content.text());

                Elements date = post.select("a div");
                String newDate = changeDate.getStandardDateForEUNeighboursEastNews(date.text());
                String mainImage = "https://euneighbourseast.eu" + doc.select(".uk-section-default .uk-container .uk-grid-margin .tm-grid-expand picture img").attr("src");

                Elements imagesList = doc.select(".uk-section-default .uk-container .uk-grid-margin .tm-grid-expand .uk-panel .wp-block-file object");

                NewsDto newsDto = new NewsDto(
                        title.text(), null, newShortContent, newDate, newsSource
                );

                List<ImageDto> images = new ArrayList<>();
                log.debug("getEUNeighboursEastNews() - image list created");
                images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

                imagesList.forEach((img) -> {
                    if (img.attr("data").isEmpty()) {
                        images.add(new ImageDto("",
                                img.attr("data"),
                                "image", null, false, newsDto));
                        log.debug("getEUNeighboursEastNews() - image added to image list");
                    }
                });
                newsDto.setImages(images);

                log.debug("getEUNeighboursEastNews() - images set to newsDto");
                newsService.saveNews(newsDto);
                log.debug("getEUNeighboursEastNews() - main news saved");
            }
        }
    }
    public void getDtfGovAz(Document doc) throws IOException {
        Elements allPosts = doc.select("#__layout main .minBodyHeight .newsAll .fadeInElement");
        String newsSource = "Dtf.gov.az";

        for (var post : allPosts) {
            doc = Jsoup.connect("https://dtf.gov.az" + post.select(".scaleImgOnHover").attr("href")).get();
            Elements postContent = doc.select("#__layout main .minBodyHeight .mb-16");
            Elements title = postContent.select(".text-2xl");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            Elements content = postContent.select("#article__content p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            String mainImage = postContent.select(".border-t.border-grayGreen-light .aspect-ratio-wrap img").attr("src");
            String newDate;
            if (!Objects.requireNonNull(postContent.select(".flex.items-center.justify-between .flex.space-x-4.fadeInElement")
                    .first()).select("span").text().isEmpty()) {
                Elements date = Objects.requireNonNull(postContent.select(".flex.items-center.justify-between .flex.space-x-4.fadeInElement")
                        .first()).select("span");
                newDate = changeDate.getStandardDateForDtfGovAz(date.text());
            } else {
                newDate = "Empty Date";
            }

            Elements imagesList = postContent.select(".relative .swiper-wrapper .swiper-slide div a");
            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getDtfGovAz() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            imagesList.forEach((img) -> {
                    if (!img.select(".aspect-ratio-wrap img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            img.select(".aspect-ratio-wrap img").attr("src"),
                            "image", null, false, newsDto));
                        log.debug("getDtfGovAz() - image added to image list");
                }
            });
            newsDto.setImages(images);
            log.debug("getDtfGovAz() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getDtfGovAz() - main news saved");

        }
    }
    public void getAgroEconomicsAzNews(Document doc) throws IOException {
        Elements allPosts = doc.select("main .container .row #last-news .wow.fadeIn");
        String newsSource = "Agroeconomics.az";

        for (var post : allPosts) {
            Elements title = post.select(".col-md-7 h4 a");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            doc = Jsoup.connect("https://agroeconomics.az/" + post.select(".col-md-7 h4 a").attr("href")).get();
            Elements summary = doc.select("#text .summary p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(summary.text());

            String mainImage = "https://agroeconomics.az/" + doc.select(".container .row .col-md-12 #text-info p img").attr("src");
            String newDate;
            if(!Objects.requireNonNull(post.select(".icons a").last()).text().isEmpty()) {
                Element date = post.select(".icons a").last();
                newDate = changeDate.getStandardDateForAgroeconomics(Objects.requireNonNull(date).text());
            } else {
                newDate = "No Date";
            }

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getAgroEconomicsAzNews() - image list created");

            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            newsDto.setImages(images);
            log.debug("getAgroEconomicsAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getAgroEconomicsAzNews() - main news saved");
        }
    }
    public void getReportAzNews(Document doc) throws IOException {
        Elements allPosts = doc.select("main .category-page .news-list .row .col-lg-3");
        String newsSource = "Report.az";

        for (var post : allPosts) {
            Elements title = post.select(".news-block .title .feed-news-title");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            doc = Jsoup.connect("https://report.az" +
                    post.select(".news-block .title").attr("href")).get();
            Elements content = doc.select(".content .news-inner-page .news-col .selected-news .editor-body p");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            Elements date = post.select(".news-date span");
            String newDate = changeDate.getStandardDateForReportAz(date.text());
            String mainImage = doc.select(".content .news-inner-page .news-col .selected-news .news-cover .image img").attr("src");

            Elements imagesList = doc.select(".content .news-inner-page .news-col .selected-news .editor-body .news-image");

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getReportAzNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            imagesList.forEach((img) -> {
                if (!img.select(".image img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            img.select(".image img").attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getReportAzNews() - image added to image list");
                }
            });
            newsDto.setImages(images);
            log.debug("getReportAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getReportAzNews() - main news saved");
        }
    }
    public void getHetiAzNews(Document doc) throws IOException {
        Elements allPosts = doc.select("#content #primary #main .article-content-area #dle-content .masonry-item");
        String newsSource= "Heti.az";

        for (int i = 0; i < allPosts.size() - 1; i++) {
            Element post = allPosts.get(i);
            Elements title = post.select(".post-details .entry-header .entry-title a");
            if (changeDate.newsExist(title.text(), newsSource)) {
                break;
            }
            doc = Jsoup.connect(post.select(".post-details .entry-title a").attr("href")).get();
            Elements content = doc.select("#page #dle-content #content #primary .entry-content .post_featured ");

            // Check content length
            String newShortContent = changeDate.checkNewsContentLength(content.text());

            Elements date = post.select(".post-image .post-date");
            String newDate = changeDate.getStandardDateForHetiAz(date.text());
            String mainImage = "http://heti.az" + doc.select("#page #dle-content #content #primary .entry-content .post_featured img").attr("src");

            Elements imagesList = doc.select("#page #dle-content #content #primary .entry-content .post_featured a");

            NewsDto newsDto = new NewsDto(
                    title.text(), null, newShortContent, newDate, newsSource
            );

            List<ImageDto> images = new ArrayList<>();
            log.debug("getHetiAzNews() - image list created");
            images.add(new ImageDto("", mainImage, "image", null, true, newsDto));

            imagesList.forEach((img) -> {
                if (!img.select("img").attr("src").isEmpty()) {
                    images.add(new ImageDto("",
                            "http://heti.az" + img.select("img").attr("src"),
                            "image", null, false, newsDto));
                    log.debug("getHetiAzNews() - image added to image list");
                }
            });
            newsDto.setImages(images);
            log.debug("getHetiAzNews() - images set to newsDto");
            newsService.saveNews(newsDto);
            log.debug("getHetiAzNews() - main news saved");
        }
    }
}


