package com.example.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登録商品情報Formクラス
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistCartItemForm {

	private String itemCode;
	
	@NotNull
	@Positive
	private Integer quantity;
}
