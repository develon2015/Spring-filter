package spring.config;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
  * Ϊ /getfile/* ����װ
 */
public class WebResourceGetterFilter extends HttpFilter {
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, String> map = new HashMap<String, String>() {
		
		private static final long serialVersionUID = 1L;

		{
			put("1", "/res/kasumi.jpg");
			put("2", "/res/�д�ܴ�.jpg");
		}
		
	};

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setHeader("Server", "Fast Tomcat");
		
		String path = request.getServletPath();
		if (path.matches("/getfile/.+")) {
			String key = path.replaceAll(".*/", "");
			String value = map.get(key); // ��ȡ��ʵ�� Servlet Path
			
			if (value == null) {
				response.setStatus(404);
				return;
			}
			// ��װ request
			request = new HttpServletRequestWrapper(request) {
				
				@Override
				public String getServletPath() {
					return value;
				}
				
			};
		}
		chain.doFilter(request, response);
	}

}
