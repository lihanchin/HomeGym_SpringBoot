package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Member;
import edu.ntut.project_01.homegym.model.Orders;
import org.hibernate.criterion.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface MemberService {
    //註冊(舊版)
    ResponseEntity<String> createMember(Member member);

    //登入(舊版)
    ResponseEntity<Member> login(String mail, String password);

    //更改密碼
    ResponseEntity<Map<String , Object>> changePassword(String authorizationHeader, String oldPassword, String newPassword, String newPasswordCheck);

    //已購買課程
    ResponseEntity<Map<String, Object>> findMyCourses(Integer memberId, Integer page, Integer size);

    //已完成訂單
//    ResponseEntity<List<Orders>> findOrdersByOKStatus(String status);

    //找出會員資料
    Member findMemberByToken(String authorizationHeader);
}