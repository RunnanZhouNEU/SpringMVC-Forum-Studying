<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User List</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<style type="text/css">
  @import "https://fonts.googleapis.com/css?family=Montserrat:300,400,700";
.rwd-table {
  margin: auto;
  margin-bottom:30px;
  min-width: 300px;
  border: 1px solid black;
  box-shadow: -10px 8px 2px 1px #d6deea;"
}
.rwd-table tr {
  
  
}
.rwd-table th {
  display: none;
}
.rwd-table td {
  display: block;
}
.rwd-table td:first-child {
  padding-top: .5em;
}
.rwd-table td:last-child {
  padding-bottom: .5em;
}
.rwd-table td:before {
  content: attr(data-th) ": ";
  font-weight: bold;
  width: 6.5em;
  display: inline-block;
}
@media (min-width: 480px) {
  .rwd-table td:before {
    display: none;
  }
}
.rwd-table th, .rwd-table td {
  text-align: left;
}
@media (min-width: 480px) {
  .rwd-table th, .rwd-table td {
    display: table-cell;
    padding: .25em .5em;
  }
  .rwd-table th:first-child, .rwd-table td:first-child {
    padding-left: 0;
  }
  .rwd-table th:last-child, .rwd-table td:last-child {
    padding-right: 0;
  }
}

body {
  padding: 0 2em;
  font-family: Montserrat, sans-serif;
  -webkit-font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
  color: #444;
  background: white;
}

h1 {
  font-weight: normal;
  letter-spacing: -1px;
  color: #34495E;
}

.rwd-table {
  
  color: black;
  border-radius: .4em;
  overflow: hidden;
  margin:atuo;
}

.rwd-table th, .rwd-table td {
  margin: .5em 1em;
}
@media (min-width: 480px) {
  .rwd-table th, .rwd-table td {
    padding: 1em !important;
  }
}
.rwd-table th, .rwd-table td:before {
  color: black;
}
.table-container{
	text-align:center;
	position:relative;
	height:100%;
	width:100%;
}
</style>
<body>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:choose>
	<c:when test="${isManager==true}">
		<jsp:include page="managerHeader.jsp"/>
	</c:when>
	<c:when test="${isManager==false}">
	<jsp:include page="header.jsp"/>
	</c:when>
</c:choose>
 
 
<div class="table-container">
	  <h1>User Management</h1>
	<table class="rwd-table">
	  <tr>
	    <th>Username</th>
	    <th>Credit</th>
	    <th>Created at</th>
	    <th>Email</th>
	    <th>Verification</th>
	    <th>Number of Post</th>
	    <th>Profile</th>
	    <th>Posts</th>
	    <th>Award</th>
	    <th>Deduct</th>
	  </tr>
	   <c:forEach var="user" items="${userList}">
	 <tr>
	 	<td data-th = "Username">${user.username}</td>
	 	<td data-th = "Credit">${user.credit }</td>
	 	<td data-th = "Created at">${user.created }</td>
	 	<td data-th = "Email">
	 	<c:if test="${user.email.length()==0}">
				N/A
		</c:if>
		<c:if test="${user.email.length()!=0}">
				${user.email }
		</c:if>
	 	</td>
	 	<td data-th = "Verification">
	 		<c:if test="${user.verify==false}">
				User Not Verified
			</c:if> 
			<c:if test="${user.verify==true}">
				User Verified
			</c:if> 	
	 	</td>
	 	<td data-th = "Number of Post">${map[user.id]}</td>
	 	<td data-th = "Profile"><a class = "btn btn-outline-dark"  href="${contextPath}/publicprofile/${user.id}" role = "button">
					 		Profile
		 </a></td>
		<td data-th = "Posts"><a class = "btn btn-outline-dark"  href="${contextPath}/checkpost/${user.id}?page=1" role = "button">
					 		Posts
		</a></td>
		<td data-th = "Award"><button class= "btn btn-outline-dark" onclick="callAward(${user.id})">Award</button>
					 		
		</td>
		<td data-th = "Deduct"><button class= "btn btn-outline-dark" onclick="callDeduct(${user.id})">Deduct</button>
					 		
		</td>
	 </tr>
	
	 	
	 </c:forEach>
	</table>
 </div>
 
 

 <jsp:include page="footer.jsp"/>
 <script type="text/javascript">
  function callAward(id){
	  if (confirm("Are you sure you want award this user?")){
		  var point =Number(prompt("Please enter the number of points")) ;
		  
		  if (!(point===parseInt(point,10))){
			  alert("Wrong format!");
		  }
		  if (point<=0){
			  alert("Can't be negative");
		  }
		  const xhr = new XMLHttpRequest();
			let url = 'http://localhost:8080/app/management/award/'+id+"?points="+point;
			xhr.open('POST',url);
			xhr.onload=()=>{
				console.log(xhr.response);
				location.reload();
			}
			xhr.send();
	  }
  }
  function callDeduct(id){
	  if (confirm("Are you sure you want deduct this user?")){
		  var point =Number(prompt("Please enter the number of points")) ;
		  
		  if (!(point===parseInt(point,10))){
			  alert("Wrong format!");
		  }
		  if (point<=0){
			  alert("Can't be negative");
		  }
		  const xhr = new XMLHttpRequest();
			let url = 'http://localhost:8080/app/management/punish/'+id+"?points="+point;
			xhr.open('POST',url);
			xhr.onload=()=>{
				console.log(xhr.response);
				location.reload();
			}
			xhr.send();
	  }
  }
 </script>
 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>