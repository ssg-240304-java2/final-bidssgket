package com.ssg.bidssgket.global.util.ncps3;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {

    private String originalFileName;
    private String uploadFileName;
    private String uploadFilePath;
    private String uploadFileUrl;

    @Builder(builderMethodName = "fileDtoBuilder")
    public FileDto(String originalFileName, String uploadFileName, String uploadFilePath, String uploadFileUrl) {
        this.originalFileName = originalFileName;
        this.uploadFileName = uploadFileName;
        this.uploadFilePath = uploadFilePath;
        this.uploadFileUrl = uploadFileUrl;
    }
}