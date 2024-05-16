package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * アプリケーション起動クラス
 * 
 */
@SpringBootApplication
public class Application {

	/**
	 * アプリケーションのエントリーポイントです。
	 * 
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
