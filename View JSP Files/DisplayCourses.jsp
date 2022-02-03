<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<title>Display Courses</title>
</head>
<body class="container">
	<nav aria-label="breadcrumb" style="font-size:22px; padding-top:25px">
  		<ol class="breadcrumb">
    		<li class="breadcrumb-item"><a href="DisplayCourses">Course Management</a></li>
  		</ol>
	</nav>
	<table class="table table-bordered">
		<thead>
			<tr class="text-center">
				<th>Courses</th><th>Assignments</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${courses}" var="course">
				<tr>
				<td><a href="DisplayAssignment?id=${course.id}">${course.courseName}</a></td>
				<c:choose>
					<c:when test="${course.assignments == null}">
						<td style='text-align:center'>0</td>
					</c:when>
					<c:when test="${course.assignments != null}">
						<td style='text-align:center'>${course.assignments.size()}</td>
					</c:when>
				</c:choose>
				</tr>	
			</c:forEach>
		</tbody>
	</table>
	<br><a class="h6" href="CreateCourse">Create Course</a>
	<!-- JavaScript Bundle with Popper -->
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>
</html>