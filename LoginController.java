package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/**
 * ログイン画面コントローラクラス
 * 
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

	/**
	 * ログイン選択画面を表示します。
	 * 
	 * @param model モデル
	 * @return ログイン選択画面
	 */
	@GetMapping("/loginSelect")
	public String view(Model model) {
		
		return "loginSelect";
	}
}
