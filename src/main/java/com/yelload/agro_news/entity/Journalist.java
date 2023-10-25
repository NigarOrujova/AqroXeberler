package com.yelload.agro_news.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "journalists")
public class Journalist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_name")
    private String name;

    @Column(name = "image_link", length = 500)
    private String link;

    @Column(name = "file_type")
    private String fileType;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
    @Column(name = "title")
    private String title;
    @Column(name = "fullname")
    private String fullname;
}
