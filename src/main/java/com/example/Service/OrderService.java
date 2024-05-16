package com.example.service;

import java.util.List;

import com.example.dto.CartItemInfoDto;
import com.example.dto.OrderInfoDto;
import com.example.form.OrderForm;

/**
 * 注文情報Serviceクラス
 * 
 */
public interface OrderService {

	/**
	 * 注文者情報をマッピングして返却します。
	 * 
	 * @param orderInfo 注文者情報
	 * @return 注文者情報
	 */
	public OrderInfoDto orderCheck(OrderForm orderInfo);
	
	/**
	 * 注文情報テーブルと注文詳細テーブルにレコードを追加する。
	 * 
	 * @param cartItemInfoList カート内商品情報
	 * @param amount 合計金額
	 * @param address 配送先住所
	 */
	public void orderComplete(List<CartItemInfoDto> cartItemInfoList, int amount, String address);
}
