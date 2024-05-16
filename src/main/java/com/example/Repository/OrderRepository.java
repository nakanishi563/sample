package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.OrderInfo;

/**
 * 注文情報Repositoryクラス
 */
public interface OrderRepository extends JpaRepository<OrderInfo, String>{

}
