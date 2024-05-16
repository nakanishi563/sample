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
import com.example.dto.RegistItemInfoDto;
import com.example.form.CartItemForm;
import com.example.form.RegistCartItemForm;
import com.example.form.SortForm;
import com.example.service.CartService;
import com.example.service.CategoryService;
import com.example.service.ItemService;
import com.example.util.AppUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * カート画面Controllerクラス
 * 
 */
@Controller
@RequiredArgsConstructor
public class CartController {
	
	/** 商品表示Serviceクラス */
	private final ItemService itemService;
	
	/** カテゴリServiceクラス */
	private final CategoryService categoryService;
	
	/** カートServiceクラス */
	private final CartService cartService;

	/** セッション情報 */
	private final HttpSession session;
	
	/** メッセージソース */
	private final MessageSource msgSource;
	
	/** 
	 * ホーム画面を表示します。
	 * 
	 * @return ホーム画面
	 */
	@GetMapping(UrlConst.REGISTERITEM)
	public String redirectHome() {		
		return AppUtil.doRedirect(UrlConst.HOME);
	}
	
	/**
	 * カート内の商品情報と合計金額を変更し、購入選択画面を表示します。
	 * 
	 * @param model モデル
	 * @param sortForm 検索情報
	 * @param registCartItemForm カートに登録した商品情報
	 * @param result 入力チェック
	 * @return 購入選択画面
	 */
	@PostMapping(UrlConst.REGISTERITEM)
	public String registerItem(Model model, SortForm sortForm, @Validated RegistCartItemForm registCartItemForm, BindingResult result) {
		if(result.hasErrors()) {
			return AppUtil.doRedirect(UrlConst.HOME);
		}
		
		//親カテゴリ情報取得して、モデルに追加
		AppUtil.addParentCategories(model, categoryService);
		
		//カートに登録する商品の情報を取得
		RegistItemInfoDto registItemInfoDto = itemService.registerItemInfo(registCartItemForm.getItemCode());
		model.addAttribute("registerItemInfo", registItemInfoDto);
		
		//カート内の商品情報を更新
		cartService.registCart(registCartItemForm.getItemCode(), registCartItemForm.getQuantity(), registItemInfoDto.getPrice());
		
		//表示するメッセージを取得
		String msg = AppUtil.getMessage(msgSource, MessageKeyConst.CART_ITEM_REGISTERED);
		model.addAttribute(ModelKeyConst.MESSAGE, msg);
		
		return ViewConst.REGISTITEM;
	}
	
	/**
	 * カート画面を表示します。
	 * 
	 * @param model モデル
	 * @param sortForm 検索情報
	 * @param cartItemForm カート内商品情報
	 * @return カート画面を表示
	 */
	@GetMapping(UrlConst.CART)
	public String displayCart(Model model, SortForm sortForm, CartItemForm cartItemForm) {
		
		//親カテゴリ情報取得して、モデルに追加
		AppUtil.addParentCategories(model, categoryService);
		
		//カート内商品情報の取得
		Optional<List<CartItemInfoDto>> cartItemInfos = AppUtil.getCartItemInfos(session);
		
		if(cartItemInfos.isEmpty()) {
			
			//表示するメッセージを取得
			String msg = AppUtil.getMessage(msgSource, MessageKeyConst.CART_EMPTY);
			model.addAttribute(ModelKeyConst.MESSAGE, msg);
			model.addAttribute(ModelKeyConst.TOTALPRICE, 0);
		}else {
			Integer totalPrice = (Integer) session.getAttribute(SessionKeyConst.TOTALPRICE);
			
			model.addAttribute("cartItemInfoList", cartItemInfos.get());
			model.addAttribute(ModelKeyConst.TOTALPRICE, totalPrice);
		}
		
		return ViewConst.CART;
	}
	
	/**
	 * カート内の商品の数量と合計金額を更新して、カート画面を表示します。
	 * 
	 * @param model モデル
	 * @param cartItemForm 数量を変更した商品情報
	 * @return カート画面
	 */
	@PostMapping(value = UrlConst.CART)
	public String updateCartItem(Model model, CartItemForm cartItemForm) {
		Optional<List<CartItemInfoDto>> cartItemInfos = AppUtil.getCartItemInfos(session);
		if(cartItemInfos.isEmpty()) {
			return AppUtil.doRedirect(UrlConst.CART);
		}
		
		//数量を変更した商品情報を基に、カート内の情報を更新
		cartService.editCartItemInfo(cartItemInfos.get(), cartItemForm.getItemCode(), cartItemForm.getQuantity());
		
		return AppUtil.doRedirect(UrlConst.CART);
	}
	
	/**
	 * カート内から商品を削除して、カート画面を表示します。
	 * 
	 * @param model モデル
	 * @param form 削除対象の商品情報
	 * @return カート画面
	 */
	@PostMapping(value = UrlConst.CART, params = "delete")
	public String removeCartItem(Model model, CartItemForm cartItemForm) {
		Optional<List<CartItemInfoDto>> cartItemInfos = AppUtil.getCartItemInfos(session);
		if(cartItemInfos.isEmpty()) {
			return AppUtil.doRedirect(UrlConst.CART);
		}
		
		//カート内から削除対象の商品を削除
		cartService.deleteCartItem(cartItemInfos.get(), cartItemForm.getItemCode(), cartItemForm.getPrice());
		
	    return AppUtil.doRedirect(UrlConst.CART);
	}
	
}
