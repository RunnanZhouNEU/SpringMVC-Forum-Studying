<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${userResult.username} 's profile</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<style type="text/css">
*{
font-family: 'Noto Serif', serif;
}

.form-container{
	border-radius:10px;
	border:1px solid black;
	width:90vh;
	margin:auto;
	margin-bottom:30px;
	margin-top:30px;
	text-align:left;
	box-shadow: -10px 8px 2px 1px rgba(0, 0, 170, .2);
	
}
.form-title{
	font-size:22px;
}
.row{
	display:flex;
	grid-template-columns: 1fr 1fr;
	font-size:22px;
	padding-left:5%;
	padding-bottom:10%;
}

.column{
float:left;
padding:10px;
height:60px;
}
.left{
width:40%;
}
.right{
width:50%;
}
.head{
padding:10px;
padding-top:20px;
border-bottom:1px solid black;
margin-bottom:32px;
}
</style>
<body>
<c:choose>
	<c:when test="${isManager==true}">
		<jsp:include page="managerHeader.jsp"/>
		
	</c:when>
	<c:when test="${isManager==false}">
	<jsp:include page="header.jsp"/>
		
	</c:when>
</c:choose>
 <c:set var="contextPath" value="${pageContext.request.contextPath}" />
 <div class="spacediv"></div>
 <div class="form-container">
  <header class="head">
    <h1 id="title" class="text-center">User Profile</h1>
    <h1 id="title" class="text-center" style="color:red;">${message}</h1>
  </header>
 	<div class="row">
	 	<div class="column left">Username:</div>
	 	<div class="column right">${userResult.username}</div>
		
		<div class="column left">Credit:</div>
	 	<div class="column right">${userResult.credit}</div>
	 	
	 	<div class="column left">Email:</div>
	 	<div class="column right">${userResult.email}</div>
	 	
	 	<div class="column left">Registered at:</div>
	 	<div class="column right">${userResult.duration}</div>
	 	
	 	<div class="column left">Verification:</div>
	 	<div class="column right">
		 	<c:if test="${userResult.verify==false}">
			<p>User Not Verified</p>
				<c:if test="${userResult.id==user.id}">
					<a href = "${contextPath}/verify">Verify</a>
				</c:if> 
			</c:if> 
			
			<c:if test="${userResult.verify==true}">
			<p>User Verified</p>
			</c:if> 
	 	</div>
	 	

		
		
		</div> 
		<c:if test="${isManager==false}">
			<a style="position:relative;left:36%;margin-bottom:30px;"  class="btn btn-outline-dark btn-lg prev"  role="button" href="${contextPath}/update/password" >Update Password</a>			
		</c:if> 
    	
  
</div>



	

	<jsp:include page="footer.jsp"/>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>