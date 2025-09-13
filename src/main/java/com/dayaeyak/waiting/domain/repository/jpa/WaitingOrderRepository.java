package com.dayaeyak.waiting.domain.repository.jpa;

import com.dayaeyak.waiting.domain.entity.WaitingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;


public interface WaitingOrderRepository extends JpaRepository<WaitingOrder, BigInteger> {


}

//public interface WaitingOrderRepository extends CrudRepository<WaitingOrder, BigInteger> {
//
//
//}


