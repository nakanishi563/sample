package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文情報テーブルEntityクラス
 * 
 */
@Entity
@Table(name = "ORDERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

	/**	注文コード */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_CD")
	private String orderCode;
	
	/** ユーザーID */
	@Column(name = "USER_ID")
	private String userId;
	
	/** 注文日時 */
	@Column(name = "ORDER_DATE")
	private LocalDateTime orderDate;
	
	/** 合計金額 */
	private Integer amount;
	
	/** 支払い補法 */
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	/** 注文状態 */
	private String status;
	
	/** 配送先住所 */
	private String address;
	
}
