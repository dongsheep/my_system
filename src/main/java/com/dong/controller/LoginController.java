package com.dong.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * vue demo 前后端分离开发
 * 
 * @author xiedongxiao
 *
 */

//@CrossOrigin(origins = {"http://localhost:8080", "null"}) // 解决跨域问题
@RestController
public class LoginController {

//	@CrossOrigin(origins = {"http://localhost:8080", "null"}) // 解决跨域问题
	@RequestMapping("/login.do")
	@ResponseBody
	public String login(Model model, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
		System.out.println(username + ":" + password);
		if ("admin".equals(username) && "123".equals(password)) {
			return "success";
		}
		return "failure";
	}
	
}
