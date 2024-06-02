package com.pae.server.member.domain.oauth;

import com.pae.server.member.domain.enums.Provider;
import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class OAuthInfo {
    private Provider provider;
    private String oid;
    private String profileUrl;

    public static OAuthInfo of(Provider provider, String oid, String profileUrl) {
        return new OAuthInfo(provider, oid, profileUrl);
    }
}
