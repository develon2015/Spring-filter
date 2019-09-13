<%@ page import="spring.tool.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>世界上最漂亮的女人</title>
<link rel="icon" type="image/jpg" href="<%=ContextTool.getContextPath()%>res/favicon.jpg">
<style type="text/css">
	body {
		text-align: center;
	}
</style>
</head>
<body>
	<H1><a href="<%=ContextTool.getContextPath()%>getfile/2">有村架纯</a></H1>
	<H1>${ str }</H1>
	<img src="<%=ContextTool.getContextPath()%>getfile/1"/>
	<hr>
	<img src="<%=ContextTool.getContextPath()%>res/futari.jpg"/>
</body>
</html>
<%@ page trimDirectiveWhitespaces="true"%>