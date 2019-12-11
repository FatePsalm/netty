package com.solace.springbootnetty.controller;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@RequestMapping("/index")
	public ModelAndView  index(){
		ModelAndView mav=new ModelAndView("socket");
		mav.addObject("uid", RandomUtil.randomNumbers(6));
		return mav;
	}
    @RequestMapping("/socket")
    public String  socket(){
        return "socket";
    }
}
