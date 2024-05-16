package com.example.entity;

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
 * 注文詳細テーブルEntityクラス
 */
@Entity
@Table(name = "ORDER_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailInfo {
	
	/** 注文詳細コード */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_DETAIL_CD")
	private String orderDetailCode;
	
	/** 注文コード */
	@Column(name = "ORDER_CD")
	private String orderCode;
	
	/** 商品コード */
	@Column(name = "ITEM_CD")
	private String itemCode;
	
	/** 購入価格 */
	@Column(name = "PURCHASE_PRICE")
	private Integer purchasePrice;
	
	/** 数量 */
	private Integer quantity;

}
