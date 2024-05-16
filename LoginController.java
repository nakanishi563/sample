package com.example.controller;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.common.constant.MessageKey;
import com.example.common.constant.Url;
import com.example.form.LoginForm;
import com.example.service.LoginService;
import com.example.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * ログイン画面Controllerクラス
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
	
	/** ログイン画面 Service */
	private final LoginService service;
	
	/** PasswordEncoder */
	private final PasswordEncoder passwordEncoder;
	
	/** メッセージソース */
	private final MessageSource messageSource;

	/**
	 * 初期表示
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return 表示画面
	 */
	@GetMapping(Url.LOGIN)
	public String view(Model model, LoginForm form) {
		
		return "login";
	}
	
	/**
	 * ログイン
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return 表示画面
	 */
	@PostMapping(Url.LOGIN)
	public String login(Model model, LoginForm form) {
		var userInfo = service.searchUserById(form.getUserId());
		var isCorrectUserAuth = userInfo.isPresent() 
				&& passwordEncoder.matches(form.getPassword(), userInfo.get().getPassword());
		if(isCorrectUserAuth) {
			return "redirect:/home";
		}else {
			var errorMsg = AppUtil.getMessage(messageSource, MessageKey.LOGIN_WRONG_INPUT);
			model.addAttribute("errorMsg", errorMsg);
			return "login";
		}
	}
}
