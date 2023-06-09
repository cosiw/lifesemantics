package com.lifesemantics.reservation.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret.key}")
    private String secretKey;

    private long tokenValidTime = 30 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    //객체 초기화, SecretKey를 Base64로 인코딩
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //토큰 생성
    public String createToken(String userIdx){
        Claims claims = Jwts.claims().setSubject(userIdx); //Jwt payload에 저장되는 정보 단위
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 유효시각 설정
            .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘과 secret값
            .compact();
    }
    // 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserIdx(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    //토큰에서 userIdx 추출
    public String getUserIdx(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    //토큰 유효성, 만료일자 확인
    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    //Request의 Header에서 token값 가져오기
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }
}
