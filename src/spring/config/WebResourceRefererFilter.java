package spring.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��� Server Name
 * �Ծ�̬��Դ�������������
 */
public class WebResourceRefererFilter extends HttpFilter {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if ("GET".equals(request.getMethod()) && request.getServletPath().matches(".*/.+\\..+")) { // GET���� ".../?.?" �к�׺��·��
			String referer = request.getHeader("Referer");
			String host = request.getServerName();
			if (referer == null || !referer.matches("https?://" + host + "/.*")) {
				response.setStatus(444);
				response.addHeader("Error", "You can't use this resource without " + host);
				return; // ������Ӧ
			}
		}
		chain.doFilter(request, response);
	}

}
