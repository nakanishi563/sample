package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カテゴリテーブルEntityクラス
 */
@Entity
@Table(name = "M_CATEGORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo {
	
	/** 分類コード */
	@Id
	@Column(name = "CATEGORY_CD")
	private String categoryCode;
	
	/** 親カテゴリ */
	@Column(name = "PARENT_CATEGORY_CD")
	private String parentCategoryCode;
	
	/** カテゴリ名 */
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
}
