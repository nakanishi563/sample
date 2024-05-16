package com.example.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カート内商品情報Formクラス
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemForm {

	private String itemCode;
	
	private Integer price;
	
	@NotNull
	private Integer quantity;
}
