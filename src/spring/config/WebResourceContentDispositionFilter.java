package spring.config;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
  * 为资源添加 Response 描述头信息
 */
public class WebResourceContentDispositionFilter extends HttpFilter {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = request.getServletPath();

		if (path.matches(".*/.+\\..+") && new File(request.getServletContext().getRealPath(path)).exists() ) { // 请求有后缀的文件, 如果文件存在, 添加 Content-Disposition
			String filename = URLEncoder.encode(path.replaceAll(".*/", ""), "UTF-8");
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"; filename*=UTF-8''%s", filename, filename));
		}
		chain.doFilter(request, response);
	}

}
