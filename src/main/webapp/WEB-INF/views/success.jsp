<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<style type="text/css">
*{
font-family: 'Noto Serif', serif;
}
form{
	
	
}
.form-container{
	border-radius:10px;
	border:1px solid black;
	width:70vh;
	margin:auto;
	margin-bottom:30px;
	margin-top:30px;
	text-align:center;
	box-shadow: -10px 8px 2px 1px rgba(0, 0, 170, .2);
}
.form-title{
	font-size:22px;
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
    <h1 id="title" class="text-center">Success</h1>
  </header>
  <div class="form-group">
	<p class="form-title">${successMessage}</p>
	<a class = "btn btn-outline-dark btn-lg" href= "${LastUrl}" role="button">Back</a>
	</div>
</div>
<body>
<body>


<jsp:include page="footer.jsp"/>
 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>

</html>