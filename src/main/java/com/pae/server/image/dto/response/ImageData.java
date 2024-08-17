package com.pae.server.image.dto.response;

import com.pae.server.image.domain.Image;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record ImageData(
        Long imageId,
        String imageUrl,
        int order
) {
    public static List<ImageData> from(List<Image> images) {
        return images.stream().map(image ->
            ImageData.builder()
                    .imageId(image.getId())
                    .imageUrl(image.getPhotoData().getPhotoUrl())
                    .order(image.getImageOrder())
                    .build()
        ).toList();
    }
}
