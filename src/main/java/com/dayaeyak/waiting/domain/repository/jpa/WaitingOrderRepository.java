package com.dayaeyak.waiting.domain.repository.jpa;

import com.dayaeyak.waiting.domain.entity.WaitingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;




public interface WaitingOrderRepository extends JpaRepository<WaitingOrder, Long> {


}

//public interface WaitingOrderRepository extends CrudRepository<WaitingOrder, Long> {
//
//
//}


