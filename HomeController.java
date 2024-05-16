package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.common.constant.Url;

/**
 * ホーム画面コントローラ
 */
@Controller
public class HomeController {

	/**
	 * 初期表示
	 * 
	 * @return 表示画面
	 */
	@GetMapping(Url.HOME)
	public String view() {
		return "home";
	}
}
