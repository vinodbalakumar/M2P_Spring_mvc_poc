<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script>
console.log(${pageContext.request.contextPath});
</script>

<body>
<div align="center">
<h1>Upload Users Excel</h1>

<form method="POST" action="${pageContext.request.contextPath}/uploadexcel" enctype="multipart/form-data">
    <input type="file" name="file" /><br/> <br/>
    <input type="submit" value="Submit" />
</form>
</div>
 <h1>Upload Status</h1>
<h2>Message : ${message}</h2> 
<a href="${pageContext.request.contextPath}/userview">view users</a>
</body>
</html>