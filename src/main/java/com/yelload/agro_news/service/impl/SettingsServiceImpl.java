package com.yelload.agro_news.service.impl;

import com.yelload.agro_news.dto.SettingsDto;
import com.yelload.agro_news.entity.Settings;
import com.yelload.agro_news.exception.EntityNotFoundException;
import com.yelload.agro_news.repository.SettingsRepository;
import com.yelload.agro_news.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;
    private final ModelMapper modelMapper;

    public SettingsServiceImpl(SettingsRepository settingsRepository, ModelMapper modelMapper) {
        this.settingsRepository = settingsRepository;
        this.modelMapper = modelMapper;
    }

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public SettingsDto getSettings(Long id) {
        Optional<Settings> settings = settingsRepository.findById(id);
        log.debug("Get settings and convert to SettingsDto");
        return convertEntityToDto(unwrapEntity(settings, id));
    }

    @Override
    public SettingsDto saveSettings(SettingsDto settingsDto) {
        Settings newSetting = modelMapper.map(settingsDto, Settings.class);
        log.debug("Map SettingsDto to Settings");

        settingsRepository.save(newSetting);
        log.debug("Save settings");
        return convertEntityToDto(newSetting);
    }

    @Override
    public SettingsDto updateSettings(Long id, SettingsDto settingsDto) {
        Optional<Settings> dbSettings = settingsRepository.findById(id);
        Settings settings = unwrapEntity(dbSettings, id);
        log.debug("Find settings from db");

        if (Objects.nonNull(settingsDto.getAbout()) && !"".equalsIgnoreCase(settingsDto.getAbout())) {
            log.debug("Update settings about field");
            settings.setAbout(settingsDto.getAbout());
        }
        if (Objects.nonNull(settingsDto.getAddress()) && !"".equalsIgnoreCase(settingsDto.getAddress())) {
            log.debug("Update settings address field");
            settings.setAddress(settingsDto.getAddress());
        }
        if (Objects.nonNull(settingsDto.getEmail()) && !"".equalsIgnoreCase(settingsDto.getEmail())) {
            log.debug("Update settings email field");
            settings.setEmail(settingsDto.getEmail());
        }
        if (settingsDto.getPhoneNumber() != settings.getPhoneNumber()) {
            log.debug("Update settings phoneNumber field");
            settings.setPhoneNumber(settingsDto.getPhoneNumber());
        }
        settingsRepository.save(settings);
        log.debug("Update settings");
        return convertEntityToDto(settings);
    }

    @Override
    public void deleteSettings(Long id) {
        log.debug("The settings with id: {} deleted", id);
        settingsRepository.deleteById(id);
    }

    @Override
    public List<SettingsDto> getAllSettings() {
        log.debug("Get all settings and convert to SettingsDto");
        return settingsRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private SettingsDto convertEntityToDto(Settings settings) {
        SettingsDto settingsDto = new SettingsDto();
        settingsDto.setId(settings.getId());
        settingsDto.setAbout(settings.getAbout());
        settingsDto.setAddress(settings.getAddress());
        settingsDto.setEmail(settings.getEmail());
        settingsDto.setPhoneNumber(settings.getPhoneNumber());
        log.debug("Convert Setting to SettingsDto");
        return settingsDto;
    }

    private static Settings unwrapEntity(Optional<Settings> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        throw new EntityNotFoundException(id, Settings.class);
    }
}
