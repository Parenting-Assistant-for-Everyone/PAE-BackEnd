@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    @GetMapping("/login/{provider}")
    public ResponseEntity<?> socialLogin(@PathVariable String provider) {
        String redirectUrl = oauthService.getRedirectUrl(provider);
        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(URI.create(redirectUrl))
                             .build();
    }

    @GetMapping("/callback/{provider}")
    public ResponseEntity<?> oauthCallback(@PathVariable String provider, @RequestParam String code) {
        OAuthTokenResponse tokenResponse = oauthService.handleCallback(provider, code);
        return ResponseEntity.ok(tokenResponse);
    }
}
