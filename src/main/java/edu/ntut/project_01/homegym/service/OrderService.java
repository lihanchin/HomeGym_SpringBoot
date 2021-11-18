package edu.ntut.project_01.homegym.service;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface OrderService {

    Page<Orders> findOrdersByMemberIdAndStatus(Integer memberId, String status,Pageable pageable);

    //已完成訂單
    List<Orders> findOrdersByMemberIdAndOKStatus(Integer memberId, String status);

    //算出該會員已完訂單總頁數
    Integer totalPageByStatus(Integer memberId, String status, Integer size);

    //show訂單詳細資訊(名稱、價格)
    List<Course> statusOrderDetail(Page<Orders> orders);

    List<Course> okStatusCourses(List<Orders> orders);

    //回傳當頁內容、總頁數、訂單內的課程資訊
    Map<String, Object> orderPage(Integer memberId, String status, Integer page, Integer totalPage);



    }
