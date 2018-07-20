package hust.phone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hust.phone.mapper.pojo.Messages;
import hust.phone.service.MessageService;
import hust.phone.service.TestService;

@Controller
public class TestContoller {
	
	private static final Logger logger = LoggerFactory.getLogger(TestContoller.class);
	
	@Autowired
	private TestService testServiceImpl;
	
	@Autowired
	private MessageService messageServiceImpl;
	
	@RequestMapping("/test")
	public String toTest()
	{
		logger.info("跳转到test页面");
		return "test";
	}
	
	@RequestMapping("/recieveData")
	public String receiveData(Model model)
	{
		testServiceImpl.recevie();
		return "success";
	}
	@RequestMapping("/toshow")
	public String toshowt()
	{
		return "show";
	}
	
	@RequestMapping("/show")
	@ResponseBody
	public Messages show(Model model)
	{
		Messages messages = new Messages();
		messages.setMessageid("1");
		Messages select = messageServiceImpl.select(messages);
		return select;
	}

}
