package com.example.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.common.constant.MessageKeyConst;
import com.example.common.constant.ModelKeyConst;
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
 * 検索画面Controllerクラス
 * 
 */
@Controller
@RequiredArgsConstructor
public class SortController {
	
	/** カテゴリServiceクラス */
	private final CategoryService categoryService;
	
	/** 商品表示Serviceクラス */
	private final ItemService itemService;
	
	/** メッセージソース */
	private final MessageSource msgSource;
	
	/** 
	 * ホーム画面を表示します。
	 * 
	 * @return ホーム画面
	 */
	@GetMapping(UrlConst.SORTRESULT)
	public String redirectHome() {		
		return AppUtil.doRedirect(UrlConst.HOME);
	}
	
	/**
	 * 検索結果画面を表示します。検索結果に該当する商品が無ければ、商品の代わりにメッセージを表示します。
	 * 
	 * @param model モデル
	 * @param sortForm 検索情報
	 * @param itemForm 選択した商品情報
	 * @return 検索結果画面
	 */
	@PostMapping(UrlConst.SORTRESULT)
	public String displaySortResult(Model model, SortForm sortForm, ItemForm itemForm) {
		
		String msg = "";
		
		//nullの場合は、空文字を代入
		sortForm.setCategoryCode(StringUtils.defaultIfBlank(sortForm.getCategoryCode(), ""));
		sortForm.setCategoryName(StringUtils.defaultIfBlank(sortForm.getCategoryName(), ""));
	    sortForm.setSortWord(StringUtils.defaultIfBlank(sortForm.getSortWord(), ""));
		
	    //親カテゴリ情報取得して、モデルに追加
		AppUtil.addParentCategories(model, categoryService);
		
		if(!sortForm.getCategoryCode().isBlank() || !sortForm.getSortWord().isBlank()) {
			List<ItemInfoDto> sortResultsDto = itemService.searchItemInfos(sortForm.getCategoryCode(), sortForm.getSortWord());
			
			if(!sortResultsDto.isEmpty()) {
				model.addAttribute("sortResults", sortResultsDto);
				
				return ViewConst.SORTRESULT;
				
			}else{
				if(!sortForm.getCategoryName().isBlank() && !sortForm.getSortWord().isBlank()) {
					msg =  "カテゴリ「" + sortForm.getCategoryName() + "」内には、「" + sortForm.getSortWord() + "」に該当する商品は見つかりませんでした。";
				}else if(!sortForm.getSortWord().isBlank()) {
					msg = "「" + sortForm.getSortWord()  + "」に該当する商品は見つかりませんでした。";
				}else {
					msg = AppUtil.getMessage(msgSource, MessageKeyConst.SORT_RESULTS_EMPTY);
				}
			}
		}else {
			msg = AppUtil.getMessage(msgSource, MessageKeyConst.SORT_RESULTS_EMPTY);
		}
		model.addAttribute(ModelKeyConst.MESSAGE, msg);
		
		return ViewConst.SORTRESULT;
	}
}
