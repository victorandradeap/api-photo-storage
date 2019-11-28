package com.api.photo.storage.repository;

import com.api.photo.storage.model.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
