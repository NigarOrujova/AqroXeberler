package com.yelload.agro_news.controller;

import com.yelload.agro_news.dto.SettingsDto;
import com.yelload.agro_news.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<SettingsDto> getSetting(@PathVariable Long id) {
        log.debug("Get settings by id => getSetting()");
        return new ResponseEntity<>(settingsService.getSettings(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<SettingsDto> saveSetting(@RequestBody SettingsDto settingsDto) {
        log.debug("Save settings => saveSetting()");
        return new ResponseEntity<>(settingsService.saveSettings(settingsDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SettingsDto> updateSetting(@Valid @PathVariable("id") Long id,
            @RequestBody SettingsDto settingsDto) {
        log.debug("Update settings => updateSetting()");
        return new ResponseEntity<>(settingsService.updateSettings(id, settingsDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSettings(@PathVariable("id") Long id) {
        settingsService.deleteSettings(id);
        log.debug("Delete settings by id: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SettingsDto>> getSetting() {
        log.debug("Get all settings => getSetting()");
        return new ResponseEntity<>(settingsService.getAllSettings(), HttpStatus.OK);
    }

}
