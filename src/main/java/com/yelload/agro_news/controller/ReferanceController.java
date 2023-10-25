package com.yelload.agro_news.controller;

import com.yelload.agro_news.entity.Referance;
import com.yelload.agro_news.service.ReferanceService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/referances")
public class ReferanceController {
    private final ReferanceService referanceService;
    @Autowired
    public ReferanceController(ReferanceService referanceService) {
        this.referanceService = referanceService;
    }
    @GetMapping
    public List<Referance> getAllReferances() {
        return referanceService.getAllReferances();
    }
    @GetMapping("/{id}")
    public Referance getReferanceById(@PathVariable Long id) throws NotFoundException {
        return referanceService.getReferanceById(id);
    }
    @PostMapping
    public Referance createReferance(@RequestParam("image") MultipartFile image) throws IOException {
        return referanceService.createReferance(image);
    }
    @PutMapping("/{id}")
    public Referance updateJournalist(@PathVariable Long id,
                                       @RequestParam("image") MultipartFile image,
                                      Referance journalist) throws IOException {
        return referanceService.updateReferance(id, journalist,image);
    }
    @DeleteMapping("/{id}")
    public String deleteReferance(@PathVariable Long id) {
        return referanceService.deleteReferance(id);
    }
}
