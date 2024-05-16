package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.common.constant.UrlConst;
import com.example.common.constant.ViewConst;
import com.example.dto.ItemInfoDto;
import com.example.form.ItemForm;
import com.example.form.SortForm;
import com.example.service.CategoryService;
import com.example.service.ItemService;
import com.example.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * ホーム画面Controllerクラス
 * 
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
	
	/** カテゴリServiceクラス */
	private final CategoryService categoryService;
	
	/** 商品表示Serviceクラス */
	private final ItemService itemService;

	/**
	 * ホーム画面を表示します。
	 * 
	 * @param model モデル
	 * @param sortForm 検索情報
	 * @param itemForm 商品情報
	 * @return ホーム画面
	 */
	@GetMapping(UrlConst.HOME)
	public String displayHome(Model model, SortForm sortForm, ItemForm itemForm) {
		
			//親カテゴリ情報取得して、モデルに追加
			AppUtil.addParentCategories(model, categoryService);
			
			//売り上げ件数上位５つまでの商品の情報を取得
			List<ItemInfoDto> top5ItemInfosDto = itemService.searchTop5ItemInfos();
			model.addAttribute("top5ItemInfos", top5ItemInfosDto);
			
			//登録日が新しい５つまでの商品の情報を取得
			List<ItemInfoDto> latest5ItemInfosDto = itemService.searchLatest5ItemInfos();
			model.addAttribute("latest5ItemInfos", latest5ItemInfosDto);
		
		return ViewConst.HOME;
	}
}
