<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Choose Your Role</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link href="<c:url value = "/resources/css/main.css"/>" rel = "stylesheet"> 

</head>
<style type="text/css">
  * {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.parent {
  width: 100%;
  height: 100vh;
  position: relative;
  background-color: #d6deea;
  text-align:center;
}

.child {
  width: 750px;
  padding: 55px;
  background-color: #fff;
  position:absolute;
  left:50%;
  top:50%;
  transform : translate(-50%,-50%);
  
  
  border-radius: 15px;
  box-shadow: -10px 8px 2px 1px rgba(0, 0, 170, .2);
}

.card-heading h1 {
  font-family: 'Noto Serif', serif;
  font-size: 40px;
  margin-bottom: 28px;
}

.card-description p {
  font-family: 'Noto Serif', serif;
  font-size: 30px;
}

.card-button {
  width: 92%;
/*   border: 1px solid; */
  margin-top: 4em;
  height: 50px;
  margin-left: 25px;
  
}

.btn {
  padding: 9px 28px;
  border-radius: 20px;
  outline: 0;
  font-family: 'Noto Serif', serif;
  font-size:22px;
  transition: background-color 0.5s ease;
}

.btn:hover {
  cursor: pointer;
}

.btn.user {
  width:30%;
  border: 2px solid black;
  background-color: white;
  float: left;
}

.btn.manager {
    background-color: black;
    border: 2px solid black;
    color: white;
    float: right;
}

/* Hover Animation */

button::after {
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}

.user:hover {
  color: #fff;
  background-color: black;
}

.manager:hover {
  color: black;
  background-color: #fff;
}

.next {
    width: 30px;
    height: 30px;
    border: 2px solid black;
    border-radius: 50%;
    padding: 4px 5px;
    position: absolute;
    top: 15px;
    right: 15px;
    transition: background-color 0.5s ease;
}

.next:hover {
  cursor: pointer;
  color: #fff;
  background-color: black;
}
</style>

  <c:set var="contextPath" value="${pageContext.request.contextPath}" />
  <div class="parent">
    <div class="child">
      
      <div class="card-heading">
        <h1> WELCOME TO STUDY FORUM</h1>
      </div>
      
      <div class="card-description">
        <p>
          If you are not motivated to study &#128547 
        </p>
        <p>
        Here is the right place to come &#128568.
        </p>
        <br>
        <p>
          &#128073 Explore the Study Forum and get helped with thousands of members
          in the community.
        </p>
      </div>
      
      <div class="card-button">
        <a class="btn user" href="${contextPath}/userlogin" role="button">USER</a>
        <a class="btn manager" href="${contextPath}/managerlogin" role = "button">MANAGER</a>
      </div>
      
      <div class="next">
        <i class="fa fa-arrow-right" aria-hidden="true"></i>
        
      </div>
      
    </div>
  </div>
  

<body>
	

	

	
	
	
	
	
	 <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>