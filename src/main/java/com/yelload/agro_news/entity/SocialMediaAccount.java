package com.yelload.agro_news.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "social_media")
public class SocialMediaAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_media_name")
    private String name;

    @Column(name = "social_media_link")
    private String link;

    // Moderators
    @Column(name = "social_media_is_active")
    private Boolean isActive = false;

    @Column(name = "`created_at`")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private Date updatedOn;

    public SocialMediaAccount(Long id, String name, String link, Boolean isActive, Date createdAt, Date updatedOn) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedOn = updatedOn;
    }

    public SocialMediaAccount(String name, String link, Boolean isActive, Date createdAt, Date updatedOn) {
        this.name = name;
        this.link = link;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedOn = updatedOn;
    }

    public SocialMediaAccount() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
