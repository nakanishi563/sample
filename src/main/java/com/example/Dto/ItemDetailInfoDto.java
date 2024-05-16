package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品詳細情報Dtoクラス
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailInfoDto {
	
	private String itemCode;
	
	private String itemName;
	
	private String picturePath;
	
	private Integer price;
	
	private Integer quantity;
	
	private String description;
	
	private String categoryCode;
}
