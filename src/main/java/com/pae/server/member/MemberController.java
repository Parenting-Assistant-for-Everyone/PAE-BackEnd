@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final OAuthService oAuthService;

    @Autowired
    public MemberController(MemberService memberService, OAuthService oAuthService) {
        this.memberService = memberService;
        this.oAuthService = oAuthService;
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto);
        return ResponseEntity.ok("회원가입 완료");
    }

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = memberService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }

    // 소셜 로그인 처리 (OAuth)
    @GetMapping("/oauth2/authorize/{provider}")
    public ResponseEntity<String> oauth2Login(@PathVariable String provider, @RequestParam String code) {
        String token = oAuthService.authenticate(provider, code);
        return ResponseEntity.ok("토큰: " + token);
    }
}
