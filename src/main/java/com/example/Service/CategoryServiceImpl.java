package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dto.CategoryInfoDto;
import com.example.repository.CategoryRepository;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;

/**
 * カテゴリService実装クラス
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	/** カテゴリrepositoryクラス */
	private final CategoryRepository categoryRepository;
	
	/** Dozer Mapper */
	private final Mapper mapper;
	
	/**
	 * 取得した親カテゴリの情報をマッピングして返却します。
	 * 
	 * @return 親カテゴリ情報
	 */
	@Override
	public List<CategoryInfoDto> searchParentCategories(){
		List<CategoryInfoDto> parentCategories = categoryRepository.findByParentCategoryCodeIsNullAndCategoryNameIsNotNull().stream()
				.map(categoryInfo -> mapper.map(categoryInfo, CategoryInfoDto.class))
	            .collect(Collectors.toList());
		
		return parentCategories;
	}
}
