package com.yelload.agro_news.repository;

import com.yelload.agro_news.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Optional<Settings> findFirstByIdOrderByCreatedAtDesc(Long id);
}
