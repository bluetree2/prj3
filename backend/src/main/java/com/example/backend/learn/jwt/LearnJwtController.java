package com.example.backend.learn.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/learn/jwt")
@RequiredArgsConstructor
public class LearnJwtController {

    private final JwtEncoder jwtEncoder;

    @PostMapping("sub1")
    public String sub1(@RequestBody Map<String, String> data) {
        System.out.println("data.get(\"email\") = " + data.get("email"));
        System.out.println("data.get(\"password\") = " + data.get("password"));

        // jwt 토큰 완성
        // sign 어떻게 정보(clame,payload).sign

        // claim/payload에 민감한 정보 담지 않기 ( 최소한의 정보만 )

        // 꼭 작성해야 하는 claim을 (4개)
        // 어디서 발행했는지 Issuer (iss)
        // 누구를 위한 토큰인지 Subject (sub)
        // 언제 만들었는지 Issued At (iat)
        // 언제까지 유효한지 Expiration Time (exp)

        // 우리가 필요한 것 (1개)
        //권한 여부(option)


        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .claim("sub", data.get("email"))
//                .claim("iss", "self")
                .issuer("self")

//                .claim("iat", Instant.now())
                .issuedAt(Instant.now())

//                .claim("exp", Instant.now().plusSeconds((60 * 60 * 24 * 365)))
                .expiresAt(Instant.now().plusSeconds((60 * 60 * 24 * 365)))

                .claim("scp", "admin user manager") // space 로 구분
//                .claim("password", data.get("password"))
//                .claim("roles", "")
//                .claim("nickname", "")
                .build();

        // jwt 응답
        return
                jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
                        .getTokenValue();

    }
}
