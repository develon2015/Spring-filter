# Spring-filter

# 包装 request: HttpServletRequest, 修改 ServletPath 等数据
```Java
private HashMap<String, String> map = new HashMap<String, String>() {
	
	private static final long serialVersionUID = 1L;

	{
		put("1", "/res/kasumi.jpg");
		put("2", "/res/有村架纯.jpg");
	}
	
};

@Override
public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
	response.setHeader("Server", "Fast Tomcat");
	
	String path = request.getServletPath();
	if (path.matches("/getfile/.+")) {
		String key = path.replaceAll(".*/", "");
		String value = map.get(key); // 获取真实的 Servlet Path
		
		if (value == null) {
			response.setStatus(404);
			return;
		}
		// 包装 request
		request = new HttpServletRequestWrapper(request) {
			
			@Override
			public String getServletPath() {
				return value;
			}
			
		};
	}
	chain.doFilter(request, response);
}
```

# 防盗链
```Java
@Override
public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
	if ("GET".equals(request.getMethod()) && request.getServletPath().matches(".*/.+\\..+")) { // GET请求 ".../?.?" 有后缀的路径
		String referer = request.getHeader("Referer");
		String host = request.getServerName();
		if (referer == null || !referer.matches("https?://" + host + "/.*")) {
			response.setStatus(444);
			response.addHeader("Error", "You can't use this resource without " + host);
			return; // 结束响应
		}
	}
	chain.doFilter(request, response);
}
```

# 添加文件描述
```Java
@Override
public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
	String path = request.getServletPath();

	if (path.matches(".*/.+\\..+") && new File(request.getServletContext().getRealPath(path)).exists() ) { // 请求有后缀的文件, 如果文件存在, 添加 Content-Disposition
		String filename = URLEncoder.encode(path.replaceAll(".*/", ""), "UTF-8");
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"; filename*=UTF-8''%s", filename, filename));
	}
	chain.doFilter(request, response);
}
```
