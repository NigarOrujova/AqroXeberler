package com.yelload.agro_news.controller;

import com.yelload.agro_news.dto.SocialMediaAccountDto;
import com.yelload.agro_news.service.SocialMediaAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
public class SocialMediaController {

    private final SocialMediaAccountService socialMediaAccountService;

    public SocialMediaController(SocialMediaAccountService socialMediaAccountService) {
        this.socialMediaAccountService = socialMediaAccountService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<SocialMediaAccountDto> readSocialMediaAccountByName(
            @Valid @PathVariable("id") Long id) {
        log.debug("Get social media account by name => readSocialMediaAccountByName()");
        return new ResponseEntity<>(socialMediaAccountService.readSocialMediaAccount(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveAccount(@Valid @RequestBody SocialMediaAccountDto socialMediaAccountDto) {
        socialMediaAccountService.saveSocialMediaAccount(socialMediaAccountDto);
        log.debug("Save social media account => saveAccount()");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SocialMediaAccountDto> updateAccount(@RequestBody SocialMediaAccountDto socialMediaAccountDto,
            @PathVariable("id") Long account_id) {
        log.debug("Update social media account => updateAccount()");
        return new ResponseEntity<>(
                socialMediaAccountService.updateSocialMediaAccount(socialMediaAccountDto, account_id),
                HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable("id") Long account_id) {
        socialMediaAccountService.deleteSocialMediaAccount(account_id);
        log.debug("Delete social media account by id: {} => updateAccount()", account_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SocialMediaAccountDto>> getAllAccounts() {
        log.debug("Get social media account by name => readSocialMediaAccountByName()");
        return new ResponseEntity<>(socialMediaAccountService.readSocialMediaAccountList(), HttpStatus.OK);
    }
}
