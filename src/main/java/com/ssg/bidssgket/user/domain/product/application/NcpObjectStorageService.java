package com.ssg.bidssgket.user.domain.product.application;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NcpObjectStorageService {

    private final AmazonS3 s3Client;
    private final String bucketName;

    public NcpObjectStorageService(
            @Value("${ncp.s3.access-key}") String accessKey,
            @Value("${ncp.s3.secret-key}") String secretKey,
            @Value("${ncp.s3.region}") String region,
            @Value("${ncp.s3.bucket}") String bucketName) {

        this.bucketName = bucketName;
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public String uploadFile(MultipartFile file) {
        String fileName = generateFileName(file.getOriginalFilename());
        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            log.error("Failed to upload file to NCP Object Storage", e);
            throw new RuntimeException("Failed to upload file to NCP Object Storage", e);
        }
        return getFileUrl(fileName);
    }

    private String generateFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }

    private String getFileUrl(String fileName) {
        return s3Client.getUrl(bucketName, fileName).toString();
    }
}