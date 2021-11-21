package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Member;

import java.util.Map;
import java.util.Optional;


public interface MemberService {
    //更改密碼
    Map<String, Object> changePassword(String authorizationHeader, String oldPassword, String newPassword, String newPasswordCheck);

    //找出會員資料
    Member findMemberByToken(String authorizationHeader);

    Optional<Member> findMemberByEmail(String email);

    void update(Member member);

    Map<String, Object> updateMemberInfo(Integer memberId, String name, String memberImage, String phone);

}