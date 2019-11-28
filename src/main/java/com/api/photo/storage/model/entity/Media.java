package com.api.photo.storage.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Media {

    @Builder
    public Media(String path, String originalPath, int width, int height) {
        this.path = path;
        this.originalPath = originalPath;
        this.width = width;
        this.height = height;
        this.createdAt = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(precision = 50)
    private String originalPath;

    @Column(precision = 50)
    private String path;

    private int width;

    private int height;

    @Basic(fetch = FetchType.LAZY)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
