package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.repository.OrdersRepository;
import edu.ntut.project_01.homegym.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrderServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public List<Orders> findOrdersByMemberIdAndOKStatus(Integer memberId, String status) {
        Optional<List<Orders>> okOrders = ordersRepository.findOrdersByMember_MemberIdAndOrderStatusContaining(memberId, status);
        if (okOrders.isPresent()) {
            return okOrders.get();
        }
        throw new QueryException("會員尚無已完成訂單");
    }


    @Override
    public Page<Orders> findOrdersByMemberIdAndStatus(Integer memberId, String status, Pageable pageable) {
        log.info("memberId ===> " + memberId);
        log.info("status ===> " + status);
        log.info("pageable ===> " + pageable.toString());
        Page<Orders> okOrders = ordersRepository.findOrdersByMember_MemberIdAndOrderStatusContaining(memberId, status, pageable);
        System.out.println(okOrders.getContent());
        return okOrders;
    }

    @Override
    public Integer totalPageByStatus(Integer memberId, String status, Integer size) {
        Optional<List<Orders>> okOrders = ordersRepository.findOrdersByMember_MemberIdAndOrderStatusContaining(memberId, status);
        if (okOrders.isPresent()) {
            return (int) Math.ceil(okOrders.get().size() / (double) size);
        }
        throw new QueryException("會員尚無已完成訂單");
    }

    @Override
    public Map<String, Object> statusOrderDetail(Page<Orders> orders) {
        Map<String, Object> orderDetail = new HashMap<>();
        for (Orders o : orders.getContent()) {
            orderDetail.put(o.getOrderId(), o.getCourses());
        }
        return orderDetail;
    }

    @Override
    public List<Course> okStatusCourses(List<Orders> orders) {
        List<Course> courseList = new ArrayList<>();
        String coachName;

        for (Orders o : orders) {
            Set<Course> courses = o.getCourses();
            for (Course v : courses) {
                coachName = v.getCoach().getMember().getName();
                System.out.println(coachName);
                v.setCoachName(coachName);
            }
            courseList.addAll(courses);
        }
        return courseList;
    }

    @Override
    public Map<String, Object> orderPage(Integer memberId, String status, Integer page, Integer totalPage) {
        System.out.println("進入orderpage方法====================");
        PageRequest pageRequest = PageRequest.of(page, totalPage);
        Page<Orders> currentPage = findOrdersByMemberIdAndStatus(memberId, status, pageRequest);
        Map<String, Object> orderPageResponse = new HashMap<>();
        System.out.println("currentPage.getContent()==============" + currentPage.getContent());
        System.out.println("statusOrderDetail(currentPage)==============" + statusOrderDetail(currentPage));
        orderPageResponse.put("currentPage", currentPage.getContent());
        orderPageResponse.put("totalPage", currentPage.getTotalPages());
        orderPageResponse.put("orderDetail", statusOrderDetail(currentPage));
        return orderPageResponse;
    }
}
