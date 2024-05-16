package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dto.ItemDetailInfoDto;
import com.example.dto.ItemInfoDto;
import com.example.dto.RegistItemInfoDto;
import com.example.entity.CategoryInfo;
import com.example.entity.ItemInfo;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;
import com.example.repository.OrderDetailsRepository;
import com.example.util.AppUtil;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;

/**
 * 商品表示Service実装クラス
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
	
	/** 商品表示repositoryクラス */
	private final ItemRepository itemRepository;
	
	/** カテゴリrepositoryクラス */
	private final CategoryRepository categoryRepository;
	
	/** カテゴリrepositoryクラス */
	private final OrderDetailsRepository orderDetailsRepository;
	
	/** Dozer Mapper */
	private final Mapper mapper;
	
	/**
	 * 購入件数の多い商品の情報を５つまで取得し、Dto変換したものを返却します。
	 * 
	 * @return 商品情報
	 */
	@Override
	public List<ItemInfoDto> searchTop5ItemInfos() {
		List<ItemInfoDto> top5ItemInfosDto =  orderDetailsRepository.findTop5ByOrderBySalesDesc().stream()
				.map(Object::toString)
				.map(itemRepository::findByItemCode)
				.map(itemInfo -> mapper.map(itemInfo, ItemInfoDto.class))
				.collect(Collectors.toList());
	    
	    return top5ItemInfosDto;
	}
	
	/**
	 * 登録日が新しい商品の情報を5つまで取得し、Dto変換したものを返却します。
	 * 
	 * @return 商品情報
	 */
	@Override
	public List<ItemInfoDto> searchLatest5ItemInfos() {
		List<ItemInfoDto> latest5ItemInfosDto = itemRepository.findTop5ByOrderByRegistDateDesc().stream()
				.map(itemInfo -> mapper.map(itemInfo, ItemInfoDto.class))
				.collect(Collectors.toList());
	    
	    return latest5ItemInfosDto;
	}
	
	/**
	 * 同じカテゴリコードの商品の情報を5つまで取得して、Dto変換したものを返却します。
	 * 
	 * @param itemCode 商品コード
	 * @param categoryCode カテゴリコード
	 * @return 商品情報
	 */
	@Override
	public List<ItemInfoDto> searchRelate5ItemInfos(String itemCode, String categoryCode){
		
		List<ItemInfoDto> categoryItemInfosDto = itemRepository.findTop5ByItemCodeNotAndCategoryCodeOrderByRegistDateDesc(itemCode, categoryCode).stream()
				.map(itemInfo -> mapper.map(itemInfo, ItemInfoDto.class))
				.collect(Collectors.toList());
		
		return categoryItemInfosDto;
	}
	
	/**
	 * カテゴリコードと検索ワードに該当する商品の情報を取得し、Dto変換したものを返却します。
	 * 
	 * @param parentCategoryCode 親カテゴリコード
	 * @param sortWord 検索ワード
	 * @return 商品情報
	 */
	@Override
	public List<ItemInfoDto> searchItemInfos(String parentCategoryCode, String sortWord){
		
		List<ItemInfo> itemInfos = new ArrayList<>();
		List<ItemInfoDto> itemInfosDto = new ArrayList<>();
		
		if(parentCategoryCode.isEmpty()) {
			
			//検索ワードに該当する商品の情報を取得
			itemInfos = itemRepository.findByItemNameLike(AppUtil.addWildcard(sortWord));
		}else {
			
			List<String> categoryCodes = categoryRepository.findSubcategoriesByParentCategoryCode(parentCategoryCode)
                    .stream()
                    .map(CategoryInfo::getCategoryCode)
                    .collect(Collectors.toList());
			
			if(sortWord.isEmpty()) {
				
				//カテゴリコードに該当する商品の情報を取得
				itemInfos = itemRepository.findByCategoryCodeIn(categoryCodes);
			}else {
				
				//カテゴリコードと検索ワードに該当する商品の情報を取得
				itemInfos = itemRepository.findByCategoryCodeInAndItemNameLike(categoryCodes, AppUtil.addWildcard(sortWord));
			}
		}
		
		if(!itemInfos.isEmpty()) {
			itemInfosDto = itemInfos.stream()
					.map(itemInfo -> mapper.map(itemInfo, ItemInfoDto.class))
					.collect(Collectors.toList());
		}
		
		return itemInfosDto;
	 }
	
	/**
	 * 商品詳細情報を取得して、返却します。
	 * 
	 * @param itemCode 商品コード
	 * @return 商品詳細情報
	 */
	@Override
	public ItemDetailInfoDto searchItemDetailInfo(String itemCode) {
	    Object[] itemDetailInfo = itemRepository.findItemDetailInfoByItemCode(itemCode).get(0);
	    return mapToItemDetailDto(itemDetailInfo);
	}
	
	/**
	 * カートに登録する商品の情報を取得して、Dto変換したものを返却します。
	 * 
	 * @param itemCode 商品コード
	 * @return カートに登録する商品情報
	 */
	@Override
	public RegistItemInfoDto registerItemInfo(String itemCode) {
		var itemInfo = itemRepository.findByItemCode(itemCode);
		var newItemInfo = mapper.map(itemInfo, RegistItemInfoDto.class);
		
		return newItemInfo;
	}
	
	/**
	 * 商品詳細情報をDTOクラスに変換するメソッド
	 * 
	 * @param itemDetailInfo 商品詳細情報
	 * @return 商品詳細情報
	 */
	private ItemDetailInfoDto mapToItemDetailDto(Object[] itemDetailInfo) {
	    ItemDetailInfoDto itemDetailDto = new ItemDetailInfoDto();
	    itemDetailDto.setItemCode((String) itemDetailInfo[0]);
	    itemDetailDto.setItemName((String) itemDetailInfo[1]);
	    itemDetailDto.setPicturePath((String) itemDetailInfo[2]);
	    itemDetailDto.setPrice((Integer) itemDetailInfo[3]);
	    itemDetailDto.setDescription((String) itemDetailInfo[4]);
	    itemDetailDto.setQuantity((Integer) itemDetailInfo[5]);
	    itemDetailDto.setCategoryCode((String) itemDetailInfo[6]);
	    return itemDetailDto;
	}
}
