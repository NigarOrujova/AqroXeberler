package com.yelload.agro_news.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;



@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 100)
    @NotBlank(message="Please enter about")
    private String about;
    @Size(min = 1, max = 1000)
    @NotBlank(message="Please enter address")
    private String address;
    @NotBlank(message="Please enter phone number")
    @Column(name = "phone_number")
    private String phoneNumber;
    @NotBlank(message="Please enter email address")
    @Email
    @Column(name = "email", unique = true)
    private String email;

    // Moderators
    @Column(name = "is_public")
    private boolean isPublic = false;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private Date updatedOn;

    public Settings(Long id, String about, String address, String phoneNumber, String email, boolean isPublic, Date createdAt, Date updatedOn) {
        this.id = id;
        this.about = about;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.updatedOn = updatedOn;
    }

    public Settings( String about, String address, String phoneNumber, String email, boolean isPublic, Date createdAt, Date updatedOn) {
        this.about = about;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.updatedOn = updatedOn;
    }

    public Settings() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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
