package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.common.constant.SessionKeyConst;
import com.example.dto.CartItemInfoDto;
import com.example.entity.ItemInfo;
import com.example.repository.ItemRepository;
import com.example.util.AppUtil;
import com.github.dozermapper.core.Mapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * カートService実装クラス
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

	/** セッション情報 */
	private final HttpSession session;
	
	/** 商品表示repositoryクラス */
	private final ItemRepository itemDisplayRepository;
	
	/** Dozer Mapper */
	private final Mapper mapper;
	
	/** 
	 * カートに商品を登録し、登録済みの商品については数量の変更のみを行い、カート情報を返却する。
	 * 
	 * @param itemCode 商品コード
	 * @param quantity 数量
	 * @param price 価格
	 */
	@Override
	public void registCart(String itemCode, Integer quantity, Integer price) {
		
		Optional<List<CartItemInfoDto>> cartItemInfosOpt = AppUtil.getCartItemInfos(session);
		List<CartItemInfoDto> cartItemInfos = new ArrayList<>();
		Integer totalPrice  = 0;
		if(!cartItemInfosOpt.isEmpty()) {
			totalPrice = (Integer)session.getAttribute(SessionKeyConst.TOTALPRICE);
			cartItemInfos = cartItemInfosOpt.get();
		}
		
		//カート内に同じ商品があれば商品情報を代入
		Optional<CartItemInfoDto> existingItem = cartItemInfos.stream()
	            .filter(item -> item.getItemCode().equals(itemCode))
	            .findFirst();

		//同じ商品は、数量の増加
	    if (existingItem.isPresent()) {
	    	existingItem.get().setQuantity((existingItem.get().getQuantity()) + (quantity));
	    } else { 
	    	
	    	//商品情報の取得
	        ItemInfo itemInfo = itemDisplayRepository.findByItemCode(itemCode);
	        
	        //マッピング
	        CartItemInfoDto newItemInfo = mapper.map(itemInfo, CartItemInfoDto.class);
			newItemInfo.setQuantity(quantity);
			cartItemInfos.add(newItemInfo);
	    }
	        
	    session.setAttribute(SessionKeyConst.CARTITEMINFOLIST, cartItemInfos);
	        
	    totalPrice += price * quantity;
		session.setAttribute(SessionKeyConst.TOTALPRICE, totalPrice);
			
		return;
	}
	
	/**
	 * カート内の商品の数量と合計金額を更新します。
	 * 
	 */
	public void editCartItemInfo(List<CartItemInfoDto> cartItemInfos, String itemCode, Integer quantity) {
		
		for(CartItemInfoDto cartItemInfoDto : cartItemInfos) {
			if(cartItemInfoDto.getItemCode().equals(itemCode)) {
				Integer beforeTotalPrice = (Integer)session.getAttribute(SessionKeyConst.TOTALPRICE);
	        	Integer afterTotalPrice = beforeTotalPrice + (cartItemInfoDto.getPrice() * (quantity - cartItemInfoDto.getQuantity()));
	        	session.setAttribute(SessionKeyConst.TOTALPRICE, afterTotalPrice);
	        	cartItemInfoDto.setQuantity(quantity);
				break;
			}
		}
		
	    session.setAttribute(SessionKeyConst.CARTITEMINFOLIST, cartItemInfos);

	    return;
	}
	
	/**
	 * カート内から削除対象の商品を削除します。
	 * 
	 */
	public void deleteCartItem(List<CartItemInfoDto> cartItemInfos, String itemCode, Integer price) {
		Integer quantity = cartItemInfos.stream()
				.filter(cartItemInfo -> cartItemInfo.getItemCode().equals(itemCode))
				.mapToInt(CartItemInfoDto::getQuantity)
				.findFirst()
				.orElse(0);
		
		cartItemInfos.removeIf(cartItemInfo -> cartItemInfo.getItemCode().equals(itemCode));

	    Integer totalPrice = (Integer)session.getAttribute(SessionKeyConst.TOTALPRICE) - price * quantity;
	    session.setAttribute(SessionKeyConst.CARTITEMINFOLIST, cartItemInfos);
	    session.setAttribute(SessionKeyConst.TOTALPRICE, totalPrice);
	}
}
