package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.service.PageService;

@Controller
public class PageController {
	
	@Autowired
	private PageService pageService;

	@RequestMapping("index")
	public ModelAndView toPage(){
		ModelAndView mv = new ModelAndView("index");
		String ad1 = pageService.queryAd1();
		mv.addObject("bigAD",ad1);
		String ad2 = pageService.queryAd2();
		mv.addObject("rightAD",ad2);
		return mv;
	}
}
