package com.moing.backend.global.config.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.moing.backend.domain.infra.image.application.dto.ImageFileExtension;
import com.moing.backend.domain.infra.image.application.dto.ImageUrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public ImageUrlDto issuePreSignedUrl(ImageFileExtension fileExtension) {

        String valueFileExtension = fileExtension.getUploadExtension();

        String fileName = createFileName(valueFileExtension);

        GeneratePresignedUrlRequest request = getGeneratePreSignedUrlRequest(bucket, fileName, valueFileExtension);
        String url = amazonS3.generatePresignedUrl(request).toString();

        return ImageUrlDto.of(url, fileName);
    }

    public void deleteImage(String fileUrl) {
        String splitStr = ".com/";
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());

        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileExtension) {
        return UUID.randomUUID() + "." + fileExtension;
    }

    // 업로드용 Pre-Signed URL을 생성하기 때문에, PUT을 지정
    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(
            String bucket, String fileName, String fileExtension) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
        calendar.add(Calendar.MINUTE, 5);  // 5분 추가
        return calendar.getTime();
    }

}