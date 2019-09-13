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

# 实现效果
```Bash
d@MyServer:~/tomcat/bin$ curl localhost/Spring-filter/res/kasumi.jpg -IL
HTTP/1.1 200
Server: Fast Tomcat
Content-Disposition: attachment; filename="kasumi.jpg"; filename*=UTF-8''kasumi.jpg #文件描述
Accept-Ranges: bytes
ETag: W/"79027-1568247848000"
Last-Modified: Thu, 12 Sep 2019 00:24:08 GMT
Content-Type: image/jpeg
Content-Length: 79027
Date: Fri, 13 Sep 2019 13:32:37 GMT

d@MyServer:~/tomcat/bin$ wget localhost/Spring-filter/res/kasumi.jpg
--2019-09-13 09:32:47--  http://localhost/Spring-filter/res/kasumi.jpg
Resolving localhost (localhost)... ::1, 127.0.0.1
Connecting to localhost (localhost)|::1|:80... connected.
HTTP request sent, awaiting response... 444
2019-09-13 09:32:47 ERROR 444: (no description). #防盗链

d@MyServer:~/tomcat/bin$ wget localhost/Spring-filter/res/kasumi.jpg --header="Referer: http://localhost/"
--2019-09-13 09:35:32--  http://localhost/Spring-filter/res/kasumi.jpg
Resolving localhost (localhost)... ::1, 127.0.0.1
Connecting to localhost (localhost)|::1|:80... connected.
HTTP request sent, awaiting response... 200
Length: 79027 (77K) [image/jpeg]
Saving to: ‘kasumi.jpg’

kasumi.jpg                         100%[=============================================================>]  77.17K  --.-KB/s    in 0.004s

2019-09-13 09:35:32 (20.5 MB/s) - ‘kasumi.jpg’ saved [79027/79027]

d@MyServer:~/tomcat/bin$ curl localhost/Spring-filter/getfile/2 -IL
HTTP/1.1 200
Server: Fast Tomcat
Content-Disposition: attachment; filename="%E6%9C%89%E6%9D%91%E6%9E%B6%E7%BA%AF.jpg"; filename*=UTF-8''%E6%9C%89%E6%9D%91%E6%9E%B6%E7%BA%AF.jpg
Accept-Ranges: bytes
ETag: W/"79027-1568247848000"
Last-Modified: Thu, 12 Sep 2019 00:24:08 GMT
Content-Type: image/jpeg
Content-Length: 79027
Date: Fri, 13 Sep 2019 13:36:38 GMT #包装request实现隐藏文件
```
