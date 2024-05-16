package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.common.constant.MessageKeyConst;
import com.example.common.constant.ModelKeyConst;
import com.example.common.constant.SessionKeyConst;
import com.example.common.constant.UrlConst;
import com.example.common.constant.ViewConst;
import com.example.dto.CartItemInfoDto;
import com.example.dto.OrderInfoDto;
import com.example.form.OrderForm;
import com.example.service.OrderService;
import com.example.util.AppUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * 注文画面コントローラクラス
 * 
 */
@Controller
@RequiredArgsConstructor
public class OrderController {
	
	/** 注文情報Serviceクラス */
	private final OrderService orderService;

	/** セッション情報 */
	private final HttpSession session;
	
	/** メッセージソース */
	private final MessageSource msgSource;
	
	/**
	 * ホーム画面を表示します。
	 * 
	 * @return ホーム画面
	 */
	@GetMapping({UrlConst.ORDERCHECK, UrlConst.ORDERCOMPLETE})
	public String redirectHome() {
		
		return AppUtil.doRedirect(UrlConst.HOME);
	}
	
	/**
	 * 注文者情報入力画面を表示します。
	 * 
	 * @param model モデル
	 * @param orderForm 注文者情報
	 * @return 注文者情報入力画面
	 */
	@GetMapping("/order")
	public String displayOrderInput(Model model, OrderForm orderForm) {
		
		//カート内商品情報の取得
		Optional<List<CartItemInfoDto>> cartItemInfosDto = AppUtil.getCartItemInfos(session);
		
		if(cartItemInfosDto.isEmpty()) {
			return AppUtil.doRedirect(UrlConst.CART);
		}
		
		model.addAttribute("cartItemInfoList", cartItemInfosDto.get());
		model.addAttribute(ModelKeyConst.TOTALPRICE, (Integer)session.getAttribute(SessionKeyConst.TOTALPRICE));
		
		return ViewConst.ORDERINPUT;
	}
	
	/**
	 * 注文内容確認画面を表示します。
	 * 
	 * @param model モデル
	 * @param orderForm 注文者情報
	 * @param result 入力チェック
	 * @return 注文者情報入力画面か注文内容確認画面
	 */
	@PostMapping(UrlConst.ORDERCHECK)
	public String displayOrderCheck(Model model, @Validated OrderForm orderForm, BindingResult result) {
		
		//カート内商品情報の取得
		Optional<List<CartItemInfoDto>> cartItemInfosDto = AppUtil.getCartItemInfos(session);
		if(cartItemInfosDto.isEmpty()) {
			return AppUtil.doRedirect(UrlConst.CART);
		}
		
		if(result.hasErrors()) {
			model.addAttribute("cartItemInfoList", cartItemInfosDto.get());
			model.addAttribute(ModelKeyConst.TOTALPRICE, (Integer)session.getAttribute(SessionKeyConst.TOTALPRICE));
	    	
	        return ViewConst.ORDERINPUT;
	    }
	    
		//マッピングした注文者情報の取得
	    OrderInfoDto orderInfoDto = orderService.orderCheck(orderForm);
	    
	    model.addAttribute("orderInfo", orderInfoDto);
	    model.addAttribute("address", orderForm.getAddress());
	    
	    return ViewConst.ORDERCHECK;
	}
	
	/**
	 * セッション情報を削除して、注文完了画面を表示します。
	 * 
	 * @param model モデル
	 * @param orderForm 注文者情報
	 * @return 注文完了画面
	 */
	@PostMapping(UrlConst.ORDERCOMPLETE)
	public String displayOrderComplete(Model model, OrderForm orderForm) {
		
		//カート内商品情報の取得
		Optional<List<CartItemInfoDto>> cartItemInfosDto = AppUtil.getCartItemInfos(session);
		if(cartItemInfosDto.isEmpty()) {
			return AppUtil.doRedirect(UrlConst.CART);
		}
		
		//注文情報、注文詳細の追加
		orderService.orderComplete(cartItemInfosDto.get(), (Integer)session.getAttribute(SessionKeyConst.TOTALPRICE), orderForm.getAddress());
		
		session.removeAttribute(SessionKeyConst.CARTITEMINFOLIST);
		session.removeAttribute(SessionKeyConst.TOTALPRICE);
		
		//表示するメッセージを取得
		String msg = AppUtil.getMessage(msgSource, MessageKeyConst.ORDER_COMPLETED);
		model.addAttribute(ModelKeyConst.MESSAGE, msg);
			
		return ViewConst.ORDERCOMPLETE;
	}

}
