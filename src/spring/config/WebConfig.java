package spring.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import spring.mvc.Index;
import spring.tool.Log;

@EnableWebMvc
@ComponentScan(basePackageClasses = { Index.class })
public class WebConfig extends WebMvcConfigurerAdapter {
	
	static {
		Log.d("MVC started!");
	}
	
	// 配置视图解析器
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/Views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	// 配置容器 "默认Servlet"
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable("default"); // 启用 Servlet 容器的 "默认Servlet", 优先级处于最低位置
	}

	// 配置消息转换器
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter smc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		smc.setDefaultCharset(Charset.forName("UTF-8"));
		smc.setWriteAcceptCharset(false); // 禁用 Accept Header
		converters.add(smc);
		
		ByteArrayHttpMessageConverter bytemc = new ByteArrayHttpMessageConverter();
		converters.add(bytemc);
	}
	
}
