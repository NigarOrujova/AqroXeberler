package com.yelload.agro_news.repository;

import com.yelload.agro_news.entity.SocialMediaAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialMediaAccountRepository extends JpaRepository<SocialMediaAccount, Long> {

}
