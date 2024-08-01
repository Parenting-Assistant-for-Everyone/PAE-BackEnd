package com.pae.server.member.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoGeocodingResponse {
    private Meta meta;
    private List<Document> documents;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        @JsonProperty("total_count")
        private int totalCount;
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {
        @JsonProperty("address")
        private Address address;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Address {
            @JsonProperty("region_1depth_name")
            private String region1depthName;

            @JsonProperty("region_2depth_name")
            private String region2depthName;

            @JsonProperty("region_3depth_name")
            private String region3depthName;
        }
    }
}
