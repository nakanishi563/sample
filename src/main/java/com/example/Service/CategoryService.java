package com.example.service;

import java.util.List;

import com.example.dto.CategoryInfoDto;

/**
 * カテゴリServiceクラス
 */
public interface CategoryService {
	
	/**
	 * 親カテゴリの情報を取得して、返却します。
	 * 
	 * @return 検索結果
	 */
	public List<CategoryInfoDto> searchParentCategories();
}
