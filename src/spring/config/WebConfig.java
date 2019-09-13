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
	
	// ������ͼ������
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/Views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	// �������� "Ĭ��Servlet"
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable("default"); // ���� Servlet ������ "Ĭ��Servlet", ���ȼ��������λ��
	}

	// ������Ϣת����
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter smc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		smc.setDefaultCharset(Charset.forName("UTF-8"));
		smc.setWriteAcceptCharset(false); // ���� Accept Header
		converters.add(smc);
		
		ByteArrayHttpMessageConverter bytemc = new ByteArrayHttpMessageConverter();
		converters.add(bytemc);
	}
	
}
