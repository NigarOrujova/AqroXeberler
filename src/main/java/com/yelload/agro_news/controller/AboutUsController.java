package com.yelload.agro_news.controller;

import com.yelload.agro_news.entity.AboutUs;
import com.yelload.agro_news.entity.Journalist;
import com.yelload.agro_news.service.AboutUsService;
import com.yelload.agro_news.service.JournalistService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/aboutUs")
public class AboutUsController {
    private final AboutUsService aboutUsService;

    @Autowired
    public AboutUsController(AboutUsService aboutUsService) {
        this.aboutUsService = aboutUsService;
    }

    @GetMapping
    public List<AboutUs> getAllAboutUs() {
        return aboutUsService.getAllAboutUs();
    }

    @GetMapping("/{id}")
    public AboutUs getAboutUsById(@PathVariable Long id) throws NotFoundException {
        return aboutUsService.getAboutUsById(id);
    }

    @PostMapping
    public AboutUs createAboutUs(@RequestParam("image") MultipartFile image,
                                       @RequestParam("title") String title,
                                       @RequestParam("description") String description,
                                       @RequestParam("isMain") boolean isMain) throws IOException {
        return aboutUsService.createAboutUs(image, title, description,isMain);
    }
    @PutMapping("/{id}")
    public AboutUs updateAboutUs(@PathVariable Long id,
                                 @RequestParam("image") MultipartFile image,
                                  AboutUs aboutUs) throws IOException {
        return aboutUsService.updateAboutUs(id, aboutUs,image);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return aboutUsService.deleteAboutUs(id);
    }
}
