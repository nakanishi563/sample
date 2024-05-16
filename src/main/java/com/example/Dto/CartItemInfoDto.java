package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カート内商品情報Dtoクラス
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemInfoDto {

	private String itemCode;
	
	private String itemName;
	
	private Integer price;
	
	private String picturePath;
	
	private Integer quantity;
}
