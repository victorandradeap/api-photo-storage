package com.api.photo.storage.service;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;

@Service
public class MinioService {

    private static final String BUCKET_NAME = "photos";
    private static final String PREFIX_CONTENT_TYPE = "image/";

    private MinioClient minioClient;

    public MinioService(@Value("${storage.host}") String host,
                        @Value("${storage.port}") String port,
                        @Value("${storage.accessKey}") String accessKey,
                        @Value("${storage.secretKey}") String secretKey) throws InvalidEndpointException, InvalidPortException {
        minioClient = new MinioClient(host, Integer.parseInt(port), accessKey, secretKey);
    }

    public String uploadMedia(String objectName, InputStream stream, Long size, String contentType) {
        try {
            minioClient.putObject(BUCKET_NAME, objectName, stream, size, null, null, PREFIX_CONTENT_TYPE + contentType);
            return BUCKET_NAME + "/" + objectName;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
