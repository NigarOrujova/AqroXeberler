package com.yelload.agro_news.service.impl;

import com.yelload.agro_news.dto.SocialMediaAccountDto;
import com.yelload.agro_news.entity.SocialMediaAccount;
import com.yelload.agro_news.exception.EntityNotFoundException;
import com.yelload.agro_news.repository.SocialMediaAccountRepository;
import com.yelload.agro_news.service.SocialMediaAccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialMediaAccountServiceImpl implements SocialMediaAccountService {
    private final SocialMediaAccountRepository socialMediaAccountRepository;
    private final ModelMapper modelMapper;

    public SocialMediaAccountServiceImpl(SocialMediaAccountRepository socialMediaAccountRepository,
            ModelMapper modelMapper) {
        this.socialMediaAccountRepository = socialMediaAccountRepository;
        this.modelMapper = modelMapper;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public SocialMediaAccountDto readSocialMediaAccount(Long id) {
        Optional<SocialMediaAccount> socialMediaAccount = socialMediaAccountRepository
                .findById(id);

        log.debug("Get social media account from db and convert to dto");
        return convertEntityToDto(unwrapEntity(socialMediaAccount));
    }

    @Override
    public SocialMediaAccountDto saveSocialMediaAccount(SocialMediaAccountDto socialMediaAccountDto) {
        SocialMediaAccount socialMediaAccount = modelMapper.map(socialMediaAccountDto, SocialMediaAccount.class);
        log.debug("Map SocialMediaAccountDto to SocialMediaAccount");
        socialMediaAccountRepository.save(socialMediaAccount);
        log.debug("Save social media account");
        return convertEntityToDto(socialMediaAccount);
    }

    @Override
    public SocialMediaAccountDto updateSocialMediaAccount(SocialMediaAccountDto socialMediaAccountDto, Long id) {
        Optional<SocialMediaAccount> dbSocialMediaAccount = socialMediaAccountRepository.findById(id);
        SocialMediaAccount socialMediaAccount = unwrapEntity(dbSocialMediaAccount, id);
        log.debug("Find social media account from db");

        if (Objects.nonNull(socialMediaAccountDto.getName()) && !"".equalsIgnoreCase(socialMediaAccountDto.getName())) {
            socialMediaAccount.setName(socialMediaAccountDto.getName());
            log.debug("Update social media account name field");
        }
        if (Objects.nonNull(socialMediaAccountDto.getLink()) && !"".equalsIgnoreCase(socialMediaAccountDto.getLink())) {
            socialMediaAccount.setLink(socialMediaAccountDto.getLink());
            log.debug("Update social media account link field");
        }
        socialMediaAccountRepository.save(socialMediaAccount);

        log.debug("Save social media account");
        return convertEntityToDto(socialMediaAccount);
    }

    @Override
    public void deleteSocialMediaAccount(Long id) {
        socialMediaAccountRepository.deleteById(id);
        log.debug("Delete social media account");
    }

    @Override
    public List<SocialMediaAccountDto> readSocialMediaAccountList() {
        log.debug("Get all social media accounts from db and convert to dto");
        return socialMediaAccountRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private SocialMediaAccountDto convertEntityToDto(SocialMediaAccount socialMediaAccount) {
        SocialMediaAccountDto socialMediaAccountDto = new SocialMediaAccountDto();
        socialMediaAccountDto.setId(socialMediaAccount.getId());
        socialMediaAccountDto.setName(socialMediaAccount.getName());
        socialMediaAccountDto.setLink(socialMediaAccount.getLink());

        log.debug("Convert SocialMediaAccount to SocialMediaAccountDto");
        return socialMediaAccountDto;
    }

    private static SocialMediaAccount unwrapEntity(Optional<SocialMediaAccount> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        throw new EntityNotFoundException(id, SocialMediaAccount.class);
    }

    private static SocialMediaAccount unwrapEntity(Optional<SocialMediaAccount> entity) {
        if (entity.isPresent())
            return entity.get();
        throw new EntityNotFoundException(SocialMediaAccount.class);
    }
}
