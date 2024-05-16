package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カテゴリDtoクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfoDto {

	private String categoryCode;
	
	private String categoryName;
}
