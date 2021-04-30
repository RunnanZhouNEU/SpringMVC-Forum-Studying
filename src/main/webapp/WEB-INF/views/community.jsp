<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${community.name}</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<style type="text/css">
.container{
	font-family: 'Noto Serif', serif;
	  width: 100%;
	  position: relative;
}
.card{
	margin:auto;
	margin-bottom:22px;
	font-size:22px;
}
.head{
position:relative;
font-family: 'Noto Serif', serif;
font-size:28px;
text-align:center;
margin-bottom:15px;

}
.createleft{
position:relative;
margin:10px;
font-family: 'Noto Serif', serif;
}
.createright{
position:relative;
left:58%;
margin:10px;
font-family: 'Noto Serif', serif;
}
</style>
<body>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var = "uri"  value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:choose>
	<c:when test="${isManager==true}">
		<jsp:include page="managerHeader.jsp"/>
		<div class="head">
			
		</div>
	</c:when>
	<c:when test="${isManager==false}">
	<jsp:include page="header.jsp"/>
		<div class="head">
			
		</div>
	</c:when>
</c:choose>
<div class="head">Community: ${community.name}</div>
<a class="btn btn-outline-dark btn-lg createleft"  role="button"  href="${uri}?sort=alpha">Sort By Alphabet</a>
<a class="btn btn-outline-dark btn-lg createleft"  role="button" href="${uri}?sort=date">Sort By Date</a>
 <a class="btn btn-outline-dark btn-lg createright"  href = "${contextPath}/createthreads" role="button">Create a Thread</a>
 
 <div class="container">
 <c:forEach var="threads" items="${threadsList}">
 <div class="card text-center border-dark" style="width: 38rem;box-shadow: -10px 8px 2px 1px #d6deea;">
				  <div class="card-header border-dark" style="background:#f58d42; ">
				  Thread
				  </div>
				  <div class="card-body">
				    <h5 class="card-title" style="font-size:38px">${threads.subject} </h5>
				    <p class="card-text">${threads.duration }</p>
				    <a class = "btn btn-outline-dark btn-lg"  href="${contextPath}/threads/${threads.id}?page=1" role = "button">
					 		SEE POSTS
					 </a>
					 <c:if test="${isManager==true}">
					 			<button class= "btn btn-outline-dark btn-lg" onclick="callDelete(${threads.id})">DELETE</button>
					 	</c:if>
				  </div>
			</div>
 </c:forEach>
  
 <script type="text/javascript">
	
		function callDelete(id){
			if (confirm("Are you sure you want delete this thread?!")){
				const xhr = new XMLHttpRequest();
				let url = 'http://localhost:8080/app/deletethreads/'+id;
				xhr.open('POST',url);
				xhr.onload=()=>{
					console.log(xhr.response);
					location.reload();
				}
				xhr.send();
					
			}
			
		}
	
</script>
 <jsp:include page="footer.jsp"/>
 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>