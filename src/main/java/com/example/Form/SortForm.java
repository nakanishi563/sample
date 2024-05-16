package com.example.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品検索情報Formクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortForm {
	
	private String categoryCode;
	
	private String categoryName;
	
	private String sortWord;
}
