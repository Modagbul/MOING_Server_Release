package com.moing.backend.global.utils;

import com.moing.backend.global.annotation.Util;
import com.moing.backend.global.config.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Util
@RequiredArgsConstructor
public class UpdateUtils {

    private final S3Service s3Service;

    public static <T> T getUpdatedValue(T currentValue, T oldValue) {
        if(currentValue!=null){
            return currentValue;
        }
        return oldValue;
    }

    public void deleteOldImgUrl(String currentImageUrl, String oldImageUrl) {
        if (currentImageUrl != null && oldImageUrl != null) {
            s3Service.deleteImage(oldImageUrl);
        }
    }
}
