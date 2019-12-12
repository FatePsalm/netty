package com.solace.springbootnetty.controller;

import cn.hutool.core.util.RandomUtil;
import com.solace.springbootnetty.enums.ParamEnum;
import com.solace.springbootnetty.util.ChannelPoolMap;
import io.netty.channel.Channel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

@Controller
public class IndexController {
     /**
       * 作者 CG
       * 时间 2019/12/12 10:29
       * 注释 聊天页面生成随机ID
       */
	@RequestMapping("/index")
	public ModelAndView  index(){
		ModelAndView mav=new ModelAndView("socket");
		mav.addObject("uid", RandomUtil.randomNumbers(6));
		return mav;
	}
	 /**
	   * 作者 CG
	   * 时间 2019/12/12 10:29
	   * 注释 获取在线用户
	   */
     @RequestMapping("/onlineUsers")
     @ResponseBody
     @CrossOrigin
     public Set onlineUsers(String type){
         ParamEnum.ChatType typeStr = ParamEnum.ChatType.findTypeStr(type);
         ConcurrentMap<String, Channel> map = ChannelPoolMap.getType(typeStr);
         return map.keySet();
     }

}
