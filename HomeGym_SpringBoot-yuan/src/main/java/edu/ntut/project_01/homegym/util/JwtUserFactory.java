package edu.ntut.project_01.homegym.util;

import edu.ntut.project_01.homegym.model.JwtUser;
import edu.ntut.project_01.homegym.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Member member) {
        return new JwtUser(
                member.getMemberId().toString(),
                member.getEmail(),
                member.getPassword(),
                member.getStatus(),
                mapToGrantedAuthorities(Arrays.asList(member.getRole()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}