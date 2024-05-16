package com.example.service;

import java.util.List;

import com.example.dto.ItemDetailInfoDto;
import com.example.dto.ItemInfoDto;
import com.example.dto.RegistItemInfoDto;

/**
 * 商品表示Serviceクラス
 * 
 */
public interface ItemService {

	/** 
	 * 購入件数の多い商品の情報を５つまで取得して、返却します。
	 * 
	 * @return 商品情報
	 */
	List<ItemInfoDto> searchTop5ItemInfos();
	
	/** 
	 * 登録日の新しい商品の情報を５つまで取得して、返却します。
	 * 
	 * @return 商品情報
	 */
	List<ItemInfoDto> searchLatest5ItemInfos();
	
	/**
	 * 同じカテゴリコードの商品の情報を５つまで取得して、返却します。
	 * 
	 * @param itemCode 商品コード
	 * @param categoryCode カテゴリコード
	 * @return 商品情報
	 */
	List<ItemInfoDto> searchRelate5ItemInfos(String itemCode, String categoryCode);
	
	/** 
	 * カテゴリコードと商品コードで商品検索を行い、該当する商品の情報返却します。
	 * 
	 * @param parentCategoryCode 親カテゴリコード
	 * @param sortWord 検索ワード
	 * @return 商品情報
	 */
	List<ItemInfoDto> searchItemInfos(String parentCategoryCode, String sortWord);
	
	/** 
	 * 商品コードの商品詳細情報を取得して、返却します。
	 * 
	 * @param itemCode 商品コード
	 * @return 商品詳細情報
	 */
	ItemDetailInfoDto searchItemDetailInfo(String itemCode);
	
	/** 
	 * カートに登録する商品の情報を取得して、返却します。
	 * 
	 * @param itemCode 商品コード
	 * @return カートに登録する商品情報
	 */
	RegistItemInfoDto registerItemInfo(String itemCode);
	
}
