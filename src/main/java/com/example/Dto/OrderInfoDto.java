package com.example.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注文者情報Dtoクラス
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto {
	
	private String name;
	
	private String furigana;
	
	private String phoneNumber;
	
	private String mailAddress;
	
	private String address;
	
	private LocalDate deliveryDate;
}
