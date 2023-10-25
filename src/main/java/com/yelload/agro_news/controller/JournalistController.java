package com.yelload.agro_news.controller;

import com.yelload.agro_news.entity.Journalist;
import com.yelload.agro_news.service.JournalistService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/journalists")
public class JournalistController {
    private final JournalistService journalistService;
    @Autowired
    public JournalistController(JournalistService journalistService) {
        this.journalistService = journalistService;
    }
    @GetMapping
    public List<Journalist> getAllJournalists() {
        return journalistService.getAllJournalists();
    }
    @GetMapping("/{id}")
    public Journalist getJournalistById(@PathVariable Long id) throws NotFoundException {
        return journalistService.getJournalistById(id);
    }
    @PostMapping
    public Journalist createJournalist(@RequestParam("image") MultipartFile image,
                                       @RequestParam("title") String title,
                                       @RequestParam("fullname") String fullname) throws IOException {
        return journalistService.createJournalist(image, title, fullname);
    }
    @PutMapping("/{id}")
    public Journalist updateJournalist(@PathVariable Long id,
                                       @RequestParam("image") MultipartFile image,
                                       Journalist journalist) throws IOException {
        return journalistService.updateJournalist(id, journalist,image);
    }
    @DeleteMapping("/{id}")
    public String deleteJournalist(@PathVariable Long id) {
        return journalistService.deleteJournalist(id);
    }
}
