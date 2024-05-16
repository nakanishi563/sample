package com.example.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller例外処理クラス
 * 
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	/**
	 * エラー画面を表示します。
	 * 
	 * @param ex エラー
	 * @return エラーページ
	 */
	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errormsg", ex.getMessage());
        modelAndView.setViewName("errorPage");
        return modelAndView;
    }
}
