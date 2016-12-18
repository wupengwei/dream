package com.wpw.dream.base;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


public class BaseAction {
	
	public void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain; charset=UTF-8", text);
	}
	
	public void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json; charset=UTF-8", text);
	}
	
	
	private void render(HttpServletResponse response, String contentType, String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			
		}

	}

}
