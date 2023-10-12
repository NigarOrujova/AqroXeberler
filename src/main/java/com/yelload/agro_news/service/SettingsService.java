package com.yelload.agro_news.service;

import com.yelload.agro_news.dto.SettingsDto;

import java.util.List;

public interface SettingsService {
    SettingsDto getSettings(Long id);

    SettingsDto saveSettings(SettingsDto settingsDto);

    SettingsDto updateSettings(Long id, SettingsDto settingsDto);

    void deleteSettings(Long id);

    List<SettingsDto> getAllSettings();
}
