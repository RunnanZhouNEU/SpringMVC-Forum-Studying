<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Recent Post</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<style type="text/css">

.container{
	font-family: 'Noto Serif', serif;
	  
	  position: relative;
}
.card{
	margin:auto;
	margin-bottom:22px;
	margin-top:40px;
	font-size:22px;
	min-height:300px;
}
.card-text{
	font-size:22px;
}
.column{
float:left;
}

.card-body{
	display:flex;
}
.left{
	width:20%;
	text-align:left;
	border-right:1px solid black;
}
.middle{
width:70%;
}
.right{
	width:10%;
	border-left:1px solid black;
}
.head{
position:relative;
font-family: 'Noto Serif', serif;
font-size:28px;
text-align:center;
margin-bottom:15px;

}
.next-container{
	width:100%;
	margin-top:100px;
	height:170px;
}
.next{
	position:absolute;
	left:60%;
}
.prev{
	position:absolute;
	left:30%;
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
.voter{
text-align:center;
font-size:22px;
margin-top:20px;

}
input{
outline:none;
}
</style>
<body>
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

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var = "uri"  value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:if test="${param.page==null}">
			<c:set var="num" value="2" />
		</c:if>
		<c:if test="${param.page!=null}">
			<c:set var="num" value="${param.page}" />
		</c:if>
		<c:if test="${param.sort!=null}">
			<c:set var="sortage" value="&sort=${param.sort}" />
		</c:if>
<div class="head">Recent Post</div>


 
 <div class="container">
	 <c:forEach var="post" items="${postList}">
	 		 <div class="card text-center border-dark" style="width: 100%;box-shadow: -10px 8px 2px 1px #d6deea;">
						  <div class="card-header border-dark" style="background:#7bf542; ">
						  Post
						  </div>
						  <div class="card-body">
						  	<div class="column left">
						  	
						  	<p class="card-text">Posted by:<strong> ${post.posterName}</strong></p>
						  	
						  	<p class="card-text">At ${post.duration}</p>
						  	</div>
						  	<div class="column middle">
						  	<h5 class="card-title" style="font-size:30px">${post.content} </h5>
						    
						    
							 <c:if test="${isManager==true}">
							 			<button class= "btn btn-outline-dark btn-lg" onclick="callDelete(${post.id})">DELETE</button>
							 </c:if>
						  	</div>
						  	<div class="column right">
						  	
						  	<button class= "btn btn-outline-dark" onclick="increment(${post.id})">&#11014</button>
						  	<p class="voter"  id = "${post.id}" >${post.vote}</p>
						  	<button class= "btn btn-outline-dark" onclick="decrement(${post.id})">&#11015</button>
						  	</div>
						  	
						  </div>
					</div>
	 </c:forEach>
	 
	 <div class="next-container">
		 <c:if test="${num>1}">
		 	<a  class="btn btn-outline-dark btn-lg prev"  role="button" href="${uri}?page=${num-1}" >Prev</a>
		 </c:if>
		 
	 	<c:if test="${num<maxPage}">
	 	<a  class="btn btn-outline-dark btn-lg next"  role="button" href="${uri}?page=${num+1}" >Next</a>
	 	</c:if>
	 </div>
	 
 </div>
 <jsp:include page="footer.jsp"/>
 <script type="text/javascript">
 function callDelete(id){
		if (confirm("Are you sure you want delete this Post?!")){
			const xhr = new XMLHttpRequest();
			let url = 'http://localhost:8080/app/deletepost/'+id;
			xhr.open('POST',url);
			xhr.onload=()=>{
				console.log(xhr.response);
				location.reload();	
			}
			xhr.send();
			
		}
		
	}
/*  function increment(id){
	 var value = parseInt(document.getElementById(id).innerHTML);
	 value=isNaN(value)?0:value;
	 value++;
	 document.getElementById(id).innerHTML=value;
	 const xhr = new XMLHttpRequest();
		let url = 'http://localhost:8080/app/upvote/post/'+id;
		xhr.open('POST',url);
		xhr.onload=()=>{
			console.log(xhr.response);
			
		}
		xhr.send();
 }
 function decrement(id){
	 var value = parseInt(document.getElementById(id).innerHTML);
	 value=isNaN(value)?0:value;
	 value--;
	 document.getElementById(id).innerHTML=value;
	 
	 const xhr = new XMLHttpRequest();
		let url = 'http://localhost:8080/app/downvote/post/'+id;
		xhr.open('POST',url);
		xhr.onload=()=>{
			console.log(xhr.response);
			
		}
		xhr.send();
 } */
</script>
 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>