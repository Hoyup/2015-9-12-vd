package com.vd.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vd.util.FilePathGenerator;
import com.vd.util.LoginUtil;

@Controller
public class LoginController {
	@Autowired
	private LoginUtil loginUtil;
	@Autowired
	private FilePathGenerator filePathGenerator;
	@RequestMapping("login")
	public String login(ModelMap modelMap,HttpSession session,String username,String password) {
		if(username!=null&&password!=null&&username.length()>0&&password.length()>0) {
			username.trim();
			password.trim();
			if(username.equals(loginUtil.getUsername())&&password.equals(loginUtil.getPassword())) {
				session.setAttribute("admin", "true");
				return "redirect:admin/index.html";   
			} else {
				modelMap.put("info", "login verify fail.");
				return "login";
			}
		}
		else return "login";
	}
	@RequestMapping("admin/logout")
	public String logout(ModelMap modelMap,HttpSession session) {
		session.setAttribute("admin", null);
		return "redirect:login.html";
	}
}
