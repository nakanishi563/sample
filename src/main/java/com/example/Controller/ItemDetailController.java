package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.common.constant.UrlConst;
import com.example.common.constant.ViewConst;
import com.example.dto.ItemDetailInfoDto;
import com.example.dto.ItemInfoDto;
import com.example.form.ItemForm;
import com.example.form.RegistCartItemForm;
import com.example.form.SortForm;
import com.example.service.CategoryService;
import com.example.service.ItemService;
import com.example.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * 商品詳細画面Controllerクラス
 * 
 */
@Controller
@RequiredArgsConstructor
public class ItemDetailController {
	
	/** カテゴリServiceクラス */
	private final CategoryService categoryService;
	
	/** 商品表示Serviceクラス */
	private final ItemService itemService;
	
	/** 
	 * ホーム画面を表示します。
	 * 
	 * @return ホーム画面
	 */
	@GetMapping(UrlConst.ITEMDETAIL)
	public String redirectHome() {		
		return AppUtil.doRedirect(UrlConst.HOME);
	}
	
	/**
	 * 商品詳細画面を表示します。
	 * 
	 * @param model モデル
	 * @param sortForm 検索情報
	 * @param itemForm 選択した商品情報
	 * @param registCartItemForm カートに登録した商品情報
	 * @return 商品詳細画面
	 */
	@PostMapping(UrlConst.ITEMDETAIL)
	public String displayItemDetail(Model model, SortForm sortForm, ItemForm itemForm, RegistCartItemForm registCartItemForm) {
		
		//親カテゴリ情報取得して、モデルに追加
		AppUtil.addParentCategories(model, categoryService);

		//商品コードの商品詳細情報を取得
		ItemDetailInfoDto itemDetailInfoDto = itemService.searchItemDetailInfo(itemForm.getItemCode());	
		model.addAttribute("itemDetailInfo", itemDetailInfoDto);
			
		//同じカテゴリコードの商品情報を取得
		List<ItemInfoDto>  relate5ItemsInfosDto = itemService.searchRelate5ItemInfos(itemDetailInfoDto.getItemCode(), itemDetailInfoDto.getCategoryCode());		
		model.addAttribute("relate5ItemsInfos", relate5ItemsInfosDto);
		
		return ViewConst.ITEMDETAIL;
	}
	
}
