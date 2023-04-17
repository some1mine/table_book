package site.jaeu95.table_book.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import site.jaeu95.table_book.domain.common.UserType;
import site.jaeu95.table_book.domain.common.UserVo;

import java.util.Date;
import java.util.Objects;

/**
 * Jwt 인증을 위해 Token 을 발행하고, Token 을 통해 인증하기 위한 클래스입니다.
 */
public class JwtAuthenticationProvider {
    private final String secretKey = "secretKey";
    private final long tokenValidTime = 1000L * 60 * 60 * 24;
    public String createToken(String phone, Long id, UserType userType) {
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(phone)).setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles", userType);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public UserVo getUserVo(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new UserVo(Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),
                Aes256Util.decrypt(claims.getSubject()));
    }
}
