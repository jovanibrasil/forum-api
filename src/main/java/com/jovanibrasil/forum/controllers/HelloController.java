package com.jovanibrasil.forum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping(value= "/", method=RequestMethod.GET)
	@ResponseBody // indicates that the response is not a page
	public String hello() {
		return "HelloWorld";
	}
	
}
