@Service
public class OAuthService {

    public String getRedirectUrl(String provider) {
        switch (provider.toLowerCase()) {
            case "naver":
                return naverOAuthClient.getAuthorizationUrl();
            case "google":
                return googleOAuthClient.getAuthorizationUrl();
            case "kakao":
                return kakaoOAuthClient.getAuthorizationUrl();
            case "apple":
                return appleOAuthClient.getAuthorizationUrl();
            default:
                throw new IllegalArgumentException("Unknown provider: " + provider);
        }
    }

    public OAuthTokenResponse handleCallback(String provider, String code) {
        switch (provider.toLowerCase()) {
            case "naver":
                return naverOAuthClient.getAccessToken(code);
            case "google":
                return googleOAuthClient.getAccessToken(code);
            case "kakao":
                return kakaoOAuthClient.getAccessToken(code);
            case "apple":
                return appleOAuthClient.getAccessToken(code);
            default:
                throw new IllegalArgumentException("Unknown provider: " + provider);
        }
    }
}
