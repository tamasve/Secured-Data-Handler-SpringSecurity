package com.secureddatahandlerspringbe.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT", length = 8192)
    private String advert;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT", length = 8192)
    private String content;

    @Lob
    @Column(columnDefinition = "LONGTEXT", length = 8192)
    private String link;

    public Book (String writer, String title, String advert, String content, String link) {
        this.writer = writer;
        this.title = title;
        this.advert = advert;
        this.content = content;
        this.link = link;
    }
}
