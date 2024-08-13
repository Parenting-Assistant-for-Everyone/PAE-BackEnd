package com.pae.server.image.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoData {
    private String originalName;

    private String bucket;

    private String object;

    private String photoUrl;

    public static PhotoData of(String originalName, String bucket, String object, String photoUrl) {
        return new PhotoData(originalName, bucket, object, photoUrl);
    }

    public static PhotoData generateDefaultPhotoData() {
        return new PhotoData("orgName", "tmpBucket", "tmpObject", "tmpPhotoUrl.com");
    }
}
