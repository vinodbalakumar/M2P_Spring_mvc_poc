<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>

	<!-- 	<h2>How to iterate list on JSP in Spring MVC</h2> -->

	<!-- 	<p><b>Simple List:</b><p> -->

	<%-- 	${users} --%>
	<div align="center">
		<p>
			<b>User List:</b>
		<p>
		<table border="1" >
			<tr>
				<td>Sno</td>
				<td>Name</td>
				<td>DOB</td>
				<td>Mobile</td>
			</tr>
			<c:forEach var="usr" items="${users}">
				<tr>
					<td>${usr.sno}</td>
					<td>${usr.name}</td>
					<td>${usr.dob}</td>
					<td>${usr.mobile}</td>
				</tr>
				<%-- 				<li>${usr.name}</li> --%>

			</c:forEach>
		</table>
	</div>
</body>
</html>