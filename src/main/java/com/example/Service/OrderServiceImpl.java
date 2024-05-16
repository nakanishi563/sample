package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.CartItemInfoDto;
import com.example.dto.OrderInfoDto;
import com.example.entity.OrderDetailInfo;
import com.example.entity.OrderInfo;
import com.example.entity.StockInfo;
import com.example.form.OrderForm;
import com.example.repository.OrderDetailsRepository;
import com.example.repository.OrderRepository;
import com.example.repository.StockRepository;

import lombok.RequiredArgsConstructor;

/**
 * 注文情報Service実装クラス
 * 
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	/** 注文詳細repositoryクラス */
	private final OrderDetailsRepository orderDetailsRepository;
	
	/** 注文情報repositoryクラス */
	private final OrderRepository orderRepository;
	
	/** 在庫repositoryクラス */
	private final StockRepository stockRepository;
	
	/**
	 * 注文者情報をマッピングして返却します。
	 * 
	 * @param orderForm 注文者情報
	 * @return 注文者情報
	 */
	public OrderInfoDto orderCheck(OrderForm orderForm) {
        
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        orderInfoDto.setName(orderForm.getName());
        orderInfoDto.setFurigana(orderForm.getFurigana());
        orderInfoDto.setPhoneNumber(orderForm.getPhoneNumber());
        orderInfoDto.setMailAddress(orderForm.getMailAddress());
        orderInfoDto.setAddress(orderForm.getAddress());
        orderInfoDto.setDeliveryDate(orderForm.getDeliveryDate());
        
        return orderInfoDto;
    }

	/**
	 * 注文情報テーブルと注文詳細テーブルにレコードを追加します。
	 * 
	 * @param cartItemInfoList カート内商品情報
	 * @param amount 合計金額
	 * @param address 配送先住所
	 */
	public void orderComplete(List<CartItemInfoDto> cartItemInfosDto, int amount, String address){
		
		var orderInfo = new OrderInfo();
		orderInfo.setOrderDate(LocalDateTime.now());
		
		//注文情報テーブルにレコードの追加
		OrderInfo savedOrder = orderRepository.save(orderInfo);
		String OrderCd = savedOrder.getOrderCode();
		
		for(CartItemInfoDto cartItemInfo : cartItemInfosDto) {
			var orderDetailInfo = new OrderDetailInfo();
			var stockInfo = new StockInfo();
			
			orderDetailInfo.setOrderCode(OrderCd);
			orderDetailInfo.setItemCode(cartItemInfo.getItemCode());
			orderDetailInfo.setPurchasePrice(cartItemInfo.getPrice());
			orderDetailInfo.setQuantity(cartItemInfo.getQuantity());
			
			//注文詳細テーブルにレコードの追加
			orderDetailsRepository.save(orderDetailInfo);
			
			stockInfo = stockRepository.findByItemCode(cartItemInfo.getItemCode());
			stockInfo.setQuantity(stockInfo.getQuantity() - cartItemInfo.getQuantity());
			stockInfo.setLastUpdate(LocalDateTime.now());
			
			//商品の在庫数の更新
			stockRepository.save(stockInfo);
		}
		
		orderInfo.setOrderCode(OrderCd);
		orderInfo.setAmount(amount);
		orderInfo.setAddress(address);
		orderInfo.setStatus("未配達");
		
		//注文情報テーブルに追加したレコードの情報の更新
		orderRepository.save(orderInfo);
	}
}
