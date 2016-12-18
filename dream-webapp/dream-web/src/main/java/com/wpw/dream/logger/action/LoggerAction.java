package com.wpw.dream.logger.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("logger")
public class LoggerAction {
	
Logger logger = Logger.getLogger(LoggerAction.class);
	
	@RequestMapping("/index")
	public String index() {
		logger.debug("这是debugger的的日志");
		logger.info("这是info的的日志");
		logger.error("这是error的的日志");
		return "artDialog/index";
	}

}
