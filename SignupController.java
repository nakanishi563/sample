package com.example.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.common.constant.MessageKey;
import com.example.common.constant.SignupMessage;
import com.example.common.constant.Url;
import com.example.entity.UserInfo;
import com.example.form.SignupForm;
import com.example.service.SignupService;
import com.example.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録画面Controllerクラス
 */
@Controller
@RequiredArgsConstructor
public class SignupController {
	
	/** ユーザー登録画面Serviceクラス */
	private final SignupService service;
	
	/** メッセージソース */
	private final MessageSource messageSource;
	
	/**
	 * 初期表示
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return 表示画面
	 */
	@GetMapping(Url.SIGNUP)
	public String view(Model model, SignupForm form) {
		
		return "signup";
	}
	
	/**
	 * ユーザー登録
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @param result 入力チェック結果
	 */
	@PostMapping(Url.SIGNUP)
	public void singup(Model model, @Validated SignupForm form, BindingResult result) {
		if(result.hasErrors()) {
			editGuideMessage(model, MessageKey.FORM_ERROR, true);
			return;
		}
		var userInfoOpt = service.resistUserInfo(form);
		var signupMessage = judgeMessage(userInfoOpt);
		editGuideMessage(model, signupMessage.getMessageId(), signupMessage.isError());
	}
	
	/**
	 * 画面に表示するメッセージを設定する
	 * 
	 * @param model モデル
	 * @param messageId メッセージID
	 * @param isError エラー有無
	 */
	private void editGuideMessage(Model model, String messageId, boolean isError) {
		var message = AppUtil.getMessage(messageSource, messageId);
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
	}
		
	/**
	 * ユーザー情報登録の結果メッセージキーを判断する
	 * 
	 * @param userInfoOpt ユーザー登録結果(登録済みだった場合はEmpty)
	 * @return メッセージキー
	 */
	private SignupMessage judgeMessage(Optional<UserInfo> userInfoOpt) {
		if(userInfoOpt.isEmpty()) {
			return SignupMessage.EXISTED_USER_ID;
		}else {
			return SignupMessage.SUCCEED;
		}
	}
}
