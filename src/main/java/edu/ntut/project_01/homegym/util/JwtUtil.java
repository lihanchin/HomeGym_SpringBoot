package edu.ntut.project_01.homegym.util;


import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;


@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private int JWT_TOKEN_VALIDITY;
    @Value("${jwt.issuer}")
    private String ISS;
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private Key key;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private MemberRepository memberRepository;

    @Autowired
    public JwtUtil(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String generateToken(UserDetails userDetails) {
        Member member = memberRepository.findMemberByEmail(userDetails.getUsername()).orElseThrow();
        Map<String, Object> memberInfo = new HashMap<>();
        memberInfo.put("memberId", member.getMemberId());
        memberInfo.put("email", userDetails.getUsername());
        memberInfo.put("role", userDetails.getAuthorities());

        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.add(Calendar.SECOND, JWT_TOKEN_VALIDITY);
        logger.info("產生JWT");
        return Jwts.builder()
                .setClaims(memberInfo)
                .setIssuer(ISS)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(calendar.getTime())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Map<String, Object> extractLoginResponse(String token) {
        Map<String ,Object> loginResponse = new HashMap<>();
        final Claims claims = extractAllClaim(token);
        loginResponse.put("memberId",claims.get("memberId"));
        loginResponse.put("email", claims.get("email"));
        loginResponse.put("role", claims.get("role"));
        Member member = memberRepository.findMemberByMemberId(Integer.valueOf(claims.get("memberId").toString())).orElseThrow();
        loginResponse.put("memberImage",member.getMemberImage());
        loginResponse.put("MimeType",member.getMimeType());
        loginResponse.put("memberName",member.getName());

        if(claims.get("role").equals("ROLE_COACH")){
            loginResponse.put("coachId", claims.get("coachId"));
        }
        return loginResponse;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);

    }

    public Claims extractAllClaim(String token) {
        logger.info("解析JWT");
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).requireIssuer(ISS).build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
