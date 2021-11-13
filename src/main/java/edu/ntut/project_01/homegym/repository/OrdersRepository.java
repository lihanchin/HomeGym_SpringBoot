package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,String> {
    //尋找所有「自訂狀態」的訂單
    Page<Orders> findOrdersByOrderStatusIn(Collection<String> orderStatus, Pageable pageable);

    //依照memberId尋找「自訂狀態」的訂單，並分頁
    Page<Orders> findOrdersByMember_MemberIdAndOrderStatusIn(Integer memberId, Collection<String> orderStatus, Pageable pageable);

    //依照memberId尋找「自訂狀態」的訂單
    Optional<List<Orders>> findOrdersByMember_MemberIdAndOrderStatusIn(Integer memberId, Collection<String> orderStatus);
}
