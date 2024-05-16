package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 在庫テーブルEntityクラス
 */
@Entity
@Table(name = "STOCKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockInfo {
	
	/** 在庫コード */
	@Id
	@Column(name = "STOCKS_CD")
	private String stockCode;
	
	/** 商品コード */
	@Column(name = "ITEM_CD")
	private String itemCode;
	
	/** 在庫数 */
	private Integer quantity;
	
	/** 最終更新日 */
	@Column(name = "LAST_UPDATE")
	private LocalDateTime lastUpdate;
	
}
