package hust.phone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hust.phone.utils.pojo.HttpClientUtil;

@Controller
public class httpController {
	
	@RequestMapping("/httptest")
	public void toTest()
	{
		 String url = "http://localhost:8083/SocketDemo/test";
		 HttpClientUtil.doGet(url);
	}

}
