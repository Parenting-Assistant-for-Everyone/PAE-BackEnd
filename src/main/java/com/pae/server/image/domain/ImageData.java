package com.pae.server.image.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageData {
    private String originalImageName;
    private String bucket;
    private String object;
    private String photoUrl;

    public static ImageData of(String originalImageName, String bucket, String object, String photoUrl) {
        return new ImageData(originalImageName, bucket, object, photoUrl);
    }
}
