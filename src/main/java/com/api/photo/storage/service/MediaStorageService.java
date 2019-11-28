package com.api.photo.storage.service;

import com.api.photo.storage.exceptions.InvalidMediaURLException;
import com.api.photo.storage.model.dto.MediaStorageRequestDTO;
import com.api.photo.storage.model.entity.Media;
import com.api.photo.storage.model.vo.MediaAssetsVO;
import com.api.photo.storage.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
public class MediaStorageService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MinioService minioService;

    public Media save(MediaStorageRequestDTO storeRequest) {
        String uniqueName = UUID.randomUUID().toString();

        MediaAssetsVO imageAssets;
        try {
            imageAssets = downloadMediaAssetsFromOriginalPath(storeRequest.getOriginalPath());
        } catch (MalformedURLException e) {
            throw new InvalidMediaURLException(storeRequest.getOriginalPath());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String path = minioService.uploadMedia(uniqueName, imageAssets.getStream(), imageAssets.getSize(), imageAssets.getContentType());

        Media media = Media.builder()
            .originalPath(storeRequest.getOriginalPath())
            .path(path)
            .height(imageAssets.getHeight())
            .width(imageAssets.getWidth())
            .build();
        return mediaRepository.save(media);
    }

    private MediaAssetsVO downloadMediaAssetsFromOriginalPath(String originalPath) throws IOException {
        MediaAssetsVO imageAssets = new MediaAssetsVO();

        URL url = new URL(originalPath);
        imageAssets.setStream(url.openStream());
        imageAssets.setSize(imageAssets.getStream().available());

        BufferedImage image = ImageIO.read(url);
        imageAssets.setHeight(image.getHeight());
        imageAssets.setWidth(image.getWidth());
        imageAssets.setContentType(String.valueOf(image.getType()));

        return imageAssets;
    }
}
