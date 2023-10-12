package com.yelload.agro_news.dto;

public class SettingsDto {
    private Long id;
    private String about;
    private String address;
    private String phoneNumber;
    private String email;

    public SettingsDto(Long id, String about, String address, String phoneNumber, String email) {
        this.id = id;
        this.about = about;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public SettingsDto(String about, String address, String phoneNumber, String email) {
        this.about = about;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public SettingsDto() {
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
}
