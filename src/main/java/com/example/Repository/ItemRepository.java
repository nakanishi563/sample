package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.ItemInfo;

/**
 * 商品表示Repositoryクラス
 */
public interface ItemRepository extends JpaRepository<ItemInfo, String> {
	
	/**
	 * 商品マスタテーブルから、商品コードを基に商品の検索を行います。
	 * 
	 * @param itemCode 商品コード
	 * @return 商品情報
	 */
	ItemInfo findByItemCode(String itemCode);
	
	/** 
	 * 商品マスタテーブルから、カテゴリコードで商品の検索を行います。
	 * 
	 * @param categoryCode カテゴリコード
	 * @return 商品情報
	 */
	List<ItemInfo> findByCategoryCodeIn(List<String> categoryCode);
	
	/** 
	 * 商品マスタテーブルから、検索ワードで商品の検索を行います。
	 * 
	 * @param sortWord 検索ワード
	 * @return 商品情報
	 */
	List<ItemInfo> findByItemNameLike(String sortWord);
	
	/** 
	 * 商品マスタテーブルから、カテゴリコードと検索ワードで商品の検索を行います。
	 * 
	 * @param categoryCode カテゴリコード
	 * @param sortWord 検索ワード
	 * @return 商品情報
	 */
	List<ItemInfo> findByCategoryCodeInAndItemNameLike(List<String> categoryCode, String sortWord);
	
	/**
	 * 商品マスタテーブルから、登録日が新しい商品の検索を行います。（５件まで）
	 * 
	 * @return 商品情報
	 */
	List<ItemInfo> findTop5ByOrderByRegistDateDesc();
	
	/** 
	 * 商品マスタテーブルから、カテゴリコードと同じ商品の検索を行います。（商品コード以外に５件）
	 * 
	 * @param categoryCode カテゴリコード
	 * @param itemCode 商品コード
	 * @return 商品情報
	 */
	List<ItemInfo> findTop5ByItemCodeNotAndCategoryCodeOrderByRegistDateDesc(String itemCode, String categoryCode);
	
	/** 
	 * 商品マスタテーブルと在庫管理テーブルから、商品コードを基に商品の検索を行います。
	 * 
	 * @param itemcd 商品コード
	 * @return 商品詳細情報
	 */
	@Query(value = "SELECT M_ITEM.ITEM_CD, ITEM_NAME, PICTURE_PATH, PRICE, DESCRIPTION, QUANTITY, CATEGORY_CD "
			+ "FROM M_ITEM "
			+ "INNER JOIN STOCKS ON M_ITEM.ITEM_CD = STOCKS.ITEM_CD "
			+ "WHERE M_ITEM.ITEM_CD = :itemCd ", nativeQuery = true)
	List<Object[]> findItemDetailInfoByItemCode(@Param("itemCd") String itemcd);
}
