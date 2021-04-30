<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    
  </head>
  <style type="text/css">
  .box-container{
    width: 100%;
  	
  	position: relative;
  }
  .chart{
  	margin:auto;
  	margin-top:30px;
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
	<div class="box-container">
	    <div id="piechart" class="chart" style="width: 900px; height: 500px;"></div>
	    <div id="piechart2" class="chart" style="width: 900px; height: 500px;"></div>
	</div>
    <jsp:include page="footer.jsp"/>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
    window.onload= function(){
    	getData();
    }
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);
	  async function getData(){
		  var jsonUser = {};
		  var jsonComm = {};
		  let response = await fetch("http://localhost:8080/app/management/userpost")
		  .then (response=>response.json()).then(data=>jsonUser=data);
		  let response2 = await fetch("http://localhost:8080/app/management/communityThread")
		  .then(response2=>response2.json().then(data=>jsonComm=data));
		  drawChart(jsonUser,jsonComm);
	  }
      function drawChart(jsonUser,jsonComm) {
    	  var dataArray=[]
    	  dataArray.push(['Community', 'Number of Thread']);
    	  for (const key in jsonComm){
    		  var arr = [key,jsonComm[key]];
    		  dataArray.push(arr);
    	  }
    	  console.log(dataArray);
    	  
    	  
		
        /* var data = google.visualization.arrayToDataTable([
          ['Community', 'Thread Number'],
          ['GRE (Graduate Record Examinations)',     11],
          ['TOEFL (Test of English as a Foreign Langague) ',     22],
          ['IELTS (International English Language Testing System)',  2],
          ['LeetCode (Algorithm Questions)', 2],
          ['SAT (Scholastic Assessment Test)',    7]
        ]); */
		var data = google.visualization.arrayToDataTable(dataArray);
        var options = {
          title: 'Community-Threads Number',
          is3D:true
        };
        
        
        
        var data2 = google.visualization.arrayToDataTable([
            ['Number of Post', 'Percetage of Person'],
            ["Never Post",     jsonUser['Never Post']],
            ["Post less than 5",  jsonUser["Post less than 5"]],
            ["Post less than 10",  jsonUser["Post less than 10"]],
            ["Post 10 or more", jsonUser["Post 10 or more"]],
          ]);

          var options2 = {
            title: 'Number of User-Posted Number',
            is3D:true
          };
        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
        var chart2 = new google.visualization.PieChart(document.getElementById('piechart2'));

        chart.draw(data, options);
        chart2.draw(data2,options2);
      }
    </script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  </body>
</html>