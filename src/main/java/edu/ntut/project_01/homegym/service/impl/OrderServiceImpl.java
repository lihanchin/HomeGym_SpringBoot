package edu.ntut.project_01.homegym.service.impl;

import edu.ntut.project_01.homegym.model.Course;
import edu.ntut.project_01.homegym.model.Orders;
import edu.ntut.project_01.homegym.repository.OrdersRepository;
import edu.ntut.project_01.homegym.service.OrderService;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private OrdersRepository ordersRepository;

    @Autowired
    public OrderServiceImpl(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Page<Orders> findOrdersByMemberIdAndStatus(Integer memberId, Collection<String> status, Pageable pageable) {
        Page<Orders> okOrders = ordersRepository.findOrdersByMember_MemberIdAndOrderStatusIn(memberId, status, pageable);
        if (okOrders != null) {
            return okOrders;
        }
        throw new QueryException("會員尚無已完成訂單");
    }

    @Override
    public Integer totalPageByStatus(Integer memberId, Collection<String> status, Integer size) {
        Optional<List<Orders>> okOrders = ordersRepository.findOrdersByMember_MemberIdAndOrderStatusIn(memberId, status);
        if (okOrders.isPresent() && okOrders.get().size() != 0) {
            return (int) Math.ceil(okOrders.get().size() / (double) size);
        }
        throw new QueryException("會員尚無已完成訂單");
    }

    @Override
    public Map<String, Object> statusOrderDetail(Page<Orders> orders) {
        Map<String, Object> orderDetail = new HashMap<>();

        for (Orders o : orders) {
            Set<Course> courses = o.getCourses();
            orderDetail.put(o.getOrderId().toString(), courses);
        }
        return orderDetail;
    }


    @Override
    public Map<String, Object> orderPage(Integer memberId, Collection<String> status, Integer page, Integer totalPage) {
        PageRequest pageRequest = PageRequest.of(page, totalPage);
        Page<Orders> currentPage = findOrdersByMemberIdAndStatus(memberId, status, pageRequest);
        Map<String, Object> orderPageResponse = new HashMap<>();
        orderPageResponse.put("currentPage", currentPage.getContent());
        orderPageResponse.put("totalPage", currentPage.getTotalPages());
        orderPageResponse.put("orderDetail", statusOrderDetail(currentPage));
        return orderPageResponse;
    }
}
