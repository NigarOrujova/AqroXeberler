package com.yelload.agro_news.task;

import com.yelload.agro_news.rss.NewsDataControl;
import com.yelload.agro_news.rss.NewsDataScraping;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScheduledTasks {
    private final NewsDataControl newsDataControl;
    private final NewsDataScraping newsDataScraping;

    public ScheduledTasks(NewsDataControl newsDataControl, NewsDataScraping newsDataScraping) {
        this.newsDataControl = newsDataControl;
        this.newsDataScraping = newsDataScraping;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedRate = 3600000) // Check after every hour  => 60 minutes * 60 seconds * 1000 milliseconds
    public void getRssFeed() throws IOException {

//        Long start = System.currentTimeMillis();

       //newsDataControl.getAgroGovAzNews(Jsoup.connect("https://agro.gov.az/az/news").get());
      //log.info("The getAgroGovAzNews() method worked...");
//        newsDataControl.getAtmGovAzNews(Jsoup.connect("https://atm.gov.az/az/menu/21/xeberler/").get());
//        log.info("The getAtmGovAzNews() method worked...");
//        newsDataControl.getAdauGovAzNews(Jsoup.connect("http://www.adau.edu.az/xeberler/").get());
//        log.info("The getAdauGovAzNews() method worked...");
//        newsDataControl.getAzertacNews(Jsoup.connect("https://azertag.az/bolme/agriculture").get());
//        log.info("The getAzertacNews() method worked...");
//        newsDataControl.getAxaGovAzNews(Jsoup.connect("http://www.axa.gov.az/az/news").get()); // ------
//        log.info("The getAxaGovAzNews() method worked...");
//        newsDataControl.getApaAzNews(Jsoup.connect("https://apa.az/az/economy/agrarian-industry").get()); // ------
//        log.info("The getApaAzNews() method worked...");
//        newsDataControl.getEUNeighboursEastNews(Jsoup.connect("https://euneighbourseast.eu/az/news/?topic%5B%5D=agriculture&show=").get());
//        log.info("The getEUNeighboursEastNews() method worked...");
//        newsDataControl.getDtfGovAz(Jsoup.connect("https://dtf.gov.az/news").get());
//        log.info("The getDtfGovAz() method worked...");
//        newsDataControl.getAgroEconomicsAzNews(Jsoup.connect("https://agroeconomics.az/az").get());
//        log.info("The getAgroEconomicsAzNews() method worked...");
//        newsDataControl.getReportAzNews(Jsoup.connect( "https://report.az/ask/").get());
//        log.info("The getReportAzNews() method worked...");
//        newsDataControl.getHetiAzNews(Jsoup.connect("http://heti.az/xeberler/").get());
//        log.info("The getHetiAzNews() method worked...");
//        newsDataControl.getAgrovolunteersNews(Jsoup.connect( "http://agrovolunteers.az/az/xeberler-test/").get());
//        log.info("The getAgrovolunteersNews() method worked...");

//        newsDataScraping.getBetiAzNews(Jsoup.connect("https://www.beti.az/az/pages/77/63").get());
//        log.info("The getBetiAzNews() method worked...");
//        newsDataScraping.getBmtBetiAzNews(Jsoup.connect("https://www.bmtbeti.az/").get());
//        log.info("The getBmtBetiAzNews() method worked...");
//        newsDataScraping.getAeteiAzNews(Jsoup.connect("https://www.aetei.az/az/news/4.html").get());
//        log.info("The getAeteiAzNews() method worked...");
//        newsDataScraping.getWineGrapeGovAzNews(Jsoup.connect("http://wine-grape.gov.az/az/news/").get());
//        log.info("The getWineGrapeGovAzNews() method worked...");
//        newsDataScraping.getAkiaGovAzNews(Jsoup.connect("http://akia.gov.az/az/news/15.html").get());
//        log.info("The getAkiaGovAzNews() method worked...");
//        newsDataScraping.getAqroservisGovAzNews(Jsoup.connect("http://aqroservis.gov.az/az/news/4.html").get());
//        log.info("The getAqroservisGovAzNews() method worked...");
//        newsDataScraping.getAgroTvAzerbaijanAzNews(Jsoup.connect("https://www.agrotvazerbaijan.az/categories-xeberler").get());
//        log.info("The getAgroTvAzerbaijanAzNews() method worked...");


         /*
        https://agro.gov.az/az/news  already added
        https://aim.gov.az/az/media/ -------------
        https://www.beti.az/az/pages/77/63 +
        https://www.bmtbeti.az/ +
        https://www.aetei.az/az/news/4.html +
        http://www.heti.az/xeberler/  already added
        http://wine-grape.gov.az/az/news/ +
        http://axa.gov.az/az/news  already added
        http://akia.gov.az/az/news/15.html  no image
        https://www.adau.edu.az/xeberler/  already added
        https://att.gov.az/az/updates/news  ---------- vue js
        http://aqroservis.gov.az/az/news/4.html +
        https://atm.gov.az/az/menu/21/xeberler/  already added
        https://www.teti.az/x-b-rl-r  ---------- class are different
        https://dtf.gov.az/news  already added
        https://www.agrotvazerbaijan.az/categories-xeberler +
        */
//            newsDataScraping.getTetiAzNews(Jsoup.connect("https://www.teti.az/x-b-rl-r").get());



//        Long end = System.currentTimeMillis();
//        NumberFormat formatter = new DecimalFormat("#0.00000");
//        log.info("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
    }
}
