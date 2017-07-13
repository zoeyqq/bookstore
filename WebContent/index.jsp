<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>前台首页</title>
</head>
<frameset rows="17%",*>
    <frame src="${pageContext.request.contextPath }/client/head.jsp" name="head">
    <frame src="${pageContext.request.contextPath }/client/IndexServlet?method=getAll" name="body">
</frameset>
<body>

</body>
</html>