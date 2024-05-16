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
 * 商品テーブルEntityクラス
 */
@Entity
@Table(name = "M_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfo {

	/** 商品コード */
	@Id
	@Column(name = "ITEM_CD")
	private String itemCode;
	
	/** 商品名 */
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	/** 価格 */
	private Integer price;
	
	/** 商品説明 */
	private String description;
	
	/** 分類コード */
	@Column(name = "CATEGORY_CD")
	private String categoryCode;
	
	/** 画像パス */
	@Column(name = "PICTURE_PATH")
	private String picturePath;
	
	/** 登録日 */
	@Column(name = "REGIST_DATE")
	private LocalDateTime registDate;
	
}
