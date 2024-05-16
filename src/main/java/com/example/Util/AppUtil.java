package com.example.util;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.ui.Model;

import com.example.common.constant.ModelKeyConst;
import com.example.common.constant.SessionKeyConst;
import com.example.dto.CartItemInfoDto;
import com.example.dto.CategoryInfoDto;
import com.example.service.CategoryService;

import jakarta.servlet.http.HttpSession;

/**
 * アプリケーション共通処理クラス
 * 
 */
public class AppUtil {
	
	/**
	 * メッセージIDから、プロパティファイルに定義されているメッセージを取得します。
	 * 
	 * @param messageSource メッセージソース
	 * @param messageId メッセージID
	 * @param params 置換文字群
	 * @return プロパティファイルから取得したメッセージ
	 */
	public static String getMessage(MessageSource messageSource, String messageId, Object... params) {
		return messageSource.getMessage(messageId, params, Locale.JAPAN);
	}

	/**
	 * DBのLIKE検索用に、パラメーターにワイルドカードを付与します。
	 * 
	 * @param param パラメーター
	 * @return 前後にワイルドカードが付いたパラメーター
	 */
		public static String addWildcard(String param) {
			return "%" + param + "%";
		}
		
	/**
	 * リダイレクト先のURLを受け取って、リダイレクトURLを作成します。
	 * 
	 * @param url リダイレクト先URL
	 * @return リダイレクトのURL
	 */
	public static String doRedirect(String url) {
			return "redirect:" + url;
	}
	
	/**
	 * 親カテゴリの情報を取得して、モデルに詰めます。
	 * 
	 * @param model モデル
	 * @param categoryService カテゴリサービス
	 */
	public static void addParentCategories(Model model,CategoryService categoryService) {
		
		//カテゴリマスタテーブルから親カテゴリの情報を取得
		List<CategoryInfoDto> parentCategoriesDto = categoryService.searchParentCategories();
		model.addAttribute(ModelKeyConst.PARENT_CATEGORIES, parentCategoriesDto);
	}
	
	@SuppressWarnings("unchecked")
	public static Optional<List<CartItemInfoDto>> getCartItemInfos(HttpSession session){
		
		return Optional.ofNullable((List<CartItemInfoDto>)session.getAttribute(SessionKeyConst.CARTITEMINFOLIST));
	}
}


