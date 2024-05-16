package com.example.service;

import java.util.List;

import com.example.dto.CartItemInfoDto;

/**
 * カートServiceクラス
 * 
 */
public interface CartService {

	/** 
	 * カート内の商品情報を更新します。
	 * 
	 * @param itemCode 商品コード
	 * @param quantity 数量
	 * @param price 価格
	 */
	void registCart(String itemCode, Integer quantity, Integer price);
	
	/**
	 * カート内商品の数量と合計金額を更新します。
	 * 
	 * @param itemCode 商品コード
	 * @param quantity 数量
	 */
	void editCartItemInfo(List<CartItemInfoDto> cartItemInfos, String itemCode, Integer quantity);
	
	/**
	 * カート内から対象の商品を削除と合計金額の更新をします。。
	 * 
	 * @param itemCode 商品コード
	 * @param price 価格
	 */
	void deleteCartItem(List<CartItemInfoDto> cartItemInfos, String itemCode, Integer price);
}
