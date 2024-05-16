package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.CategoryInfo;

/**
 * カテゴリテーブルRepositoryクラス
 */
public interface CategoryRepository extends JpaRepository<CategoryInfo, String>{
	
	/**
	 * カテゴリマスタテーブルから親カテゴリがnullのカテゴリの検索を行います。
	 * 
	 * @return 親カテゴリ情報
	 */
	List<CategoryInfo> findByParentCategoryCodeIsNullAndCategoryNameIsNotNull();
	
	/**
	 * 親カテゴリコードから最下層のカテゴリコードの検索を行います。
	 * 
	 * @param parentCategoryCd 親カテゴリコード
	 * @return 親カテゴリコードに関連するサブカテゴリコードの情報
	 */
	@Query(value = "SELECT * " +
            "FROM m_category " +
            "WHERE category_cd NOT IN " +
            "      (SELECT parent_category_cd " +
            "       FROM m_category " +
            "       WHERE parent_category_cd IS NOT NULL) " +
            "START WITH parent_category_cd = :parentCategoryCode " +
            "CONNECT BY PRIOR category_cd = parent_category_cd", nativeQuery = true)
	List<CategoryInfo> findSubcategoriesByParentCategoryCode(@Param("parentCategoryCode") String parentCategoryCode );
}
