package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.StockInfo;

/**
 * カテゴリテーブルRepositoryクラス
 */
public interface StockRepository extends JpaRepository<StockInfo, String>{
	
	/**
	 * 在庫テーブルから、商品コードを基に在庫数の検索を行います。
	 * 
	 * @param itemCode 商品コード
	 * @return 商品の在庫情報
	 */
	StockInfo findByItemCode(String itemCode);
}
