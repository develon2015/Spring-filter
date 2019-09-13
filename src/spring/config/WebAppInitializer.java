package spring.config;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import spring.tool.ContextTool;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	@Override
	public Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class, ContextTool.class };
	}
	
	@Override
	public String[] getServletMappings() {
		return new String[] { "/" }; // 使 DispatcherServlet 成为 "默认Servlet"
	}
	
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {
				new WebResourceGetterFilter(),
				new WebResourceRefererFilter(),
				new WebResourceContentDispositionFilter()
		};
	}
	
}
