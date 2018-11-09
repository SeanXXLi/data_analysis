package com.hsbc.eep.data.migrate.analysis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
public class BackIndexController {
 
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index() {
		
		return "backindex.html";
	}
	
}
