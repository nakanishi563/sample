package com.example.form;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文者情報Formクラス
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderForm {

	@NotNull
	@Length(min = 2, max = 30)
	private String name;
	
	@NotNull
	@Length(min = 4, max = 30)
	private String furigana;
	
	@NotNull
	@Pattern(regexp = "^0[789]0-[0-9]{4}-[0-9]{4}$", message = "有効な電話番号ではありません")
	private String phoneNumber;
	
	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9][A-Za-z0-9-]*[A-Za-z0-9]*\\.)+[A-Za-z]{2,}$", message = "有効なメールアドレスではありません")
	private String mailAddress;
	
	@NotNull
	@Length(min = 7, max = 161, message = "有効な住所を選択してください")
	private String address;
	
	@NotNull
	@Future(message = "有効な日付を選択してください")
	private LocalDate deliveryDate;
}
