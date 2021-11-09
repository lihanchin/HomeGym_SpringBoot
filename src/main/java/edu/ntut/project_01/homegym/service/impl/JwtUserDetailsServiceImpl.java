package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.repository.MemberRepository;
import edu.ntut.project_01.homegym.util.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private MemberRepository memberRepository;

    @Autowired
    public JwtUserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findMemberByEmail(username);
        if (member.isPresent()) {
                return JwtUserFactory.create(member.get());
        }
        throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    }
}
