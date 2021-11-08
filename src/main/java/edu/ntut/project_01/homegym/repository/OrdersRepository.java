package edu.ntut.project_01.homegym.repository;

import edu.ntut.project_01.homegym.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
//    Optional<List<Orders>> findOrdersByOrderStatusIn(Collection<String> orderStatus);
}
