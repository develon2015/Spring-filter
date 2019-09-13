package spring.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import spring.tool.ContextTool;

@Controller
public class Index {
	
	@GetMapping("/")
	public String index(HttpServletRequest request) {
		return String.format("redirect: %sindex%s", ContextTool.getContextPath(), 
				request.getQueryString() == null ? "" : ("?" + request.getQueryString()));
	}

	
	@GetMapping(value = { "/index", "/main" })
	public String index() {
		return "index";
	}
	
}
