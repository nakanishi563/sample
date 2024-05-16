package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.OrderDetailInfo;

/**
 * 注文詳細Repositoryクラス
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetailInfo, String>{
	
	/** 
	 * 注文詳細テーブルから、売り上げ件数の多い商品の検索を行います。（５件まで）
	 * 
	 * @return 商品コード
	 */
	@Query(value = "SELECT ITEM_CD "
			+ "FROM ("
			+ "SELECT ITEM_CD, COUNT(ITEM_CD) AS ITEM_COUNT "
			+ "FROM ORDER_DETAILS "
			+ "GROUP BY ITEM_CD "
			+ "ORDER BY COUNT(ITEM_CD) DESC) "
			+ "WHERE ROWNUM <= 5", nativeQuery = true)
	List<Object> findTop5ByOrderBySalesDesc();

}
