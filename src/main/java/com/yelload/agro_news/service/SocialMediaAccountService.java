package com.yelload.agro_news.service;

import com.yelload.agro_news.dto.SocialMediaAccountDto;

import java.util.List;

public interface SocialMediaAccountService {
    SocialMediaAccountDto saveSocialMediaAccount(SocialMediaAccountDto socialMediaAccountDto);

    SocialMediaAccountDto readSocialMediaAccount(Long id);

    List<SocialMediaAccountDto> readSocialMediaAccountList();

    SocialMediaAccountDto updateSocialMediaAccount(SocialMediaAccountDto socialMediaAccountDto, Long id);

    void deleteSocialMediaAccount(Long id);
}
