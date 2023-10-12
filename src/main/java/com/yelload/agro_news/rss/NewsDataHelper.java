package com.yelload.agro_news.rss;

import com.yelload.agro_news.entity.News;
import com.yelload.agro_news.exception.news.NewsTitleNullException;
import com.yelload.agro_news.repository.NewsRepository;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NewsDataHelper {
    private final NewsRepository newsRepository;
    public NewsDataHelper(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());


    public boolean newsExist(String title, String newsSource) {


        for (News news : newsRepository.findNewsByTitle(title)) {
            if (news.getTitle().isEmpty()) {
                log.warn("News can't saved. News title is null");
                throw new NewsTitleNullException();
            }
            if (news.getTitle().equals(title) && news.getNewsSource().equals(newsSource)) {
                log.debug("The {} news mached to {}", news.getTitle(), title);
                log.info("{} already added", newsSource);
                return true;
            }


        }

        log.debug("The {} news is new", title);
        return false;


    }
    public String getStandardDateAndChangeMonthFormat(String date) {
        String[] arrOfStr = date.split(" ");

        String day = arrOfStr[0];
        String month = getStandardMonth(arrOfStr[1]);
        String year = arrOfStr[2];

        log.debug("Get standard date and change month format");
        return day + "/" + month + "/" + year;
    }
    public String getStandardDateAndChangeDot(String date) {
        String[] arrOfStr = date.split("[.]", 0);

        String day = arrOfStr[0];
        String month = arrOfStr[1];
        String year = arrOfStr[2];

        log.debug("Get standard date and change month dot");
        return day + "/" + month + "/" + "20" + year;
    }
    public String getStandardDateForAxaGovAz(String date) {
        date = splitDate(date);
        String[] arrOfStr = date.split("-", 0);

        String day = arrOfStr[2];
        String month = arrOfStr[1];
        String year = arrOfStr[0];
        log.debug("Get standard date and AxaGovAz");
        return day + "/" + month + "/" + year;
    }
    public String getStandardDateForApaAz(String date){
        String[] arrOfStr = date.split(" ");
        String day = arrOfStr[0];
        String month = getStandardMonth(arrOfStr[1]);
        String year = arrOfStr[2];

        log.debug("Get standard date and ApaAz");
        return day + "/" + month + "/" + year;
    }
    public String getStandardDateForEUNeighboursEastNews(String date) {
        String[] arrOfStr = date.split(" ");

        String day = arrOfStr[1];
        if (day != null && day.length() > 0 && day.charAt(day.length() - 1) == ',') {
            day = day.substring(0, day.length() - 1);
        }

        String month;
        String monthAzLanguage;
        if (arrOfStr[0].contains("yun")) {
            monthAzLanguage = arrOfStr[0].replace("İyun", "Iyun");
            month = getStandardMonth(monthAzLanguage);
        }
        else if(arrOfStr[0].contains("yul")) {
            monthAzLanguage = arrOfStr[0].replace("İyul", "Iyul");
            month = getStandardMonth(monthAzLanguage);
        } else {
            month = getStandardMonth(arrOfStr[0]);
        }

        String year = arrOfStr[2];

        log.debug("Get standard date and EUNeighboursEastNews");
        return day + "/" + month + "/" + year;
    }
    public String getStandardDateForDtfGovAz(String date) {

        log.debug("Get standard date and DtfGovAz");
        return replaceDotWithSlash(splitDate(date));
    }
    public String getStandardDateForAgroeconomics(String date) {
        String[] arrOfStr = date.split(" ");

        String month = arrOfStr[1];
        String year = arrOfStr[2];

        log.debug("Get standard date and Agroeconomics");
        return month + " " + year;
    }
    public String getStandardDateForReportAz(String date) {
        String[] arrOfStr = date.split(" ");

        String day = arrOfStr[0];
        String month;
        String replaceMOnth;
        if (arrOfStr[0].contains("yun")) {
            replaceMOnth = arrOfStr[1].replace("İyun", "Iyun");
            month = getStandardMonth(replaceMOnth);
        }
        else if(arrOfStr[0].contains("yul")) {
            replaceMOnth = arrOfStr[1].replace("İyul", "Iyul");
            month = getStandardMonth(replaceMOnth);
        } else {
            month = getStandardMonth(arrOfStr[1]);
        }
        String year = arrOfStr[3];

        log.debug("Get standard date and ReportAz");
        return day + "/" + month + "/" + year;
    }
    public String getStandardDateForHetiAz(String date) {
        String[] arrOfStr = date.split(" ");

        String day = arrOfStr[0];
        String month = getStandardMonthFromMMMFormat(arrOfStr[1]);
        String year;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
        year = dateTime.format(format);

        log.debug("Get standard date and HetiAz");
        return day + "/" + month + "/" + year;
    }
    public String splitDate(String date) {
        log.trace("splitDate() worked");
        return date.split(" ")[0];
    }
    public String replaceDotWithSlash(String date) {
        log.trace("replaceDotWithSlash() worked");
        return date.replace(".", "/");
    }
    public String getStandardMonth(String month) {
        switch (month.toLowerCase()) {
            case "yanvar" -> month = "01";
            case "fevral" -> month = "02";
            case "mart" -> month = "03";
            case "aprel" -> month = "04";
            case "may" -> month = "05";
            case "iyun" -> month = "06";
            case "iyul" -> month = "07";
            case "avqust" -> month = "08";
            case "sentyabr" -> month = "09";
            case "oktyabr" -> month = "10";
            case "noyabr" -> month = "11";
            case "dekabr" -> month = "12";
            default -> log.warn("Wrong month format!");
        }
        log.debug("Get standard month format");
        return month;
    }
    public String getStandardMonthFromMMMFormat(String month) {
        switch (month.toLowerCase()) {
            case "jan" -> month = "01";
            case "feb" -> month = "02";
            case "mar" -> month = "03";
            case "apr" -> month = "04";
            case "may" -> month = "05";
            case "jun" -> month = "06";
            case "jul" -> month = "07";
            case "aug" -> month = "08";
            case "sep" -> month = "09";
            case "oct" -> month = "10";
            case "nov" -> month = "11";
            case "dec" -> month = "12";
            default -> log.warn("Wrong month format!");
        }
        return month;
    }
    public String checkNewsContentLength(String content) {
        log.debug("News content length checked.");
        if (content.length() <= 6000) return content;
        return content.substring(0, 6000);
    }
    public String chooseContent(Elements content1, Elements content2, Elements content3) {
        if (content1.text().length() > content2.text().length()
                && content1.text().length() > content3.text().length()) {
            log.trace("content1.text() with the length: {} chosen. Other elements size are: {} and {}",
                    content1.text().length(), content2.text().length(), content3.text().length());
            return content1.text();
        } else if (content2.text().length() > content1.text().length()
                && content2.text().length() > content3.text().length()) {
            log.trace("content2.text() with the length: {} chosen. Other elements size are: {} and {}",
                    content1.text().length(), content2.text().length(), content3.text().length());
            return content2.text();
        }

        log.trace("content3.text() with the length: {} chosen. Other elements size are: {} and {}",
                content1.text().length(), content2.text().length(), content3.text().length());
        return content3.text();
    }

}
