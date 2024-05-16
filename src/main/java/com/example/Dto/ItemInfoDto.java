package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品情報表示用Dtoクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoDto {

	private String itemCode;
	
	private String itemName;
	
	private String picturePath;
	
	private Integer price;
}
