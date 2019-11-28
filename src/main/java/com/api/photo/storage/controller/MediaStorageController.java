package com.api.photo.storage.controller;

import com.api.photo.storage.model.dto.MediaStorageRequestDTO;
import com.api.photo.storage.model.entity.Media;
import com.api.photo.storage.service.MediaStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController("/storage")
public class MediaStorageController {

    @Autowired
    private MediaStorageService mediaStorageService;

    @PostMapping
    public ResponseEntity save(@RequestBody MediaStorageRequestDTO mediaRequest) throws URISyntaxException {
        Media media = mediaStorageService.save(mediaRequest);
        URI location = createGetURLById(media.getId());
        return ResponseEntity.created(location).build();
    }

    private URI createGetURLById(long id) throws URISyntaxException {
        return new URI("HOST" + ":" + "PORT" + "/photo" + "/" + id);
    }
}
