package com.ssg.bidssgket.global.util.ncps3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${ncp.s3.bucket-name}")
    private String bucketName;


    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }
//    public List<FileDto> uploadFilesSample(List<MultipartFile> multipartFiles){
//
//        return uploadFiles(multipartFiles, uploadDir);
//    }

    //NOTICE: filePath의 맨 앞에 /는 안붙여도됨. ex) history/images
    public List<FileDto> uploadFiles(List<MultipartFile> multipartFiles, String filePath) {

        List<FileDto> s3files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {

//                String keyName = filePath + "/" + uploadFileName;


                // S3에 폴더 및 파일 업로드
                amazonS3Client.putObject(
                        new PutObjectRequest(bucketName, uploadFileName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));

                // S3에 업로드한 폴더 및 파일 URL
                uploadFileUrl = "https://kr.object.ncloudstorage.com/"+ bucketName + "/" + uploadFileName;
                // https://kr.object.ncloudstorage.com/subao-file/파일명
            } catch (IOException e) {
                e.printStackTrace();
            }

            s3files.add(
                    FileDto.fileDtoBuilder()
                            .originalFileName(originalFileName)
                            .uploadFileName(uploadFileName)
                            .uploadFilePath(filePath)
                            .uploadFileUrl(uploadFileUrl)
                            .build());
        }

        return s3files;
    }
}
