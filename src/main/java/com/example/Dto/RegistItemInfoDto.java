package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 登録商品情報Dtoクラス
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistItemInfoDto {

	private String itemName;
	
	private String picturePath;
	
	private Integer price;
}
