<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<title>Display Assignments</title>
</head>
<body class="container">
	<nav aria-label="breadcrumb" style="font-size:22px; padding-top:25px; --bs-breadcrumb-divider: '>'">
  		<ol class="breadcrumb">
    		<li class="breadcrumb-item"><a href="DisplayCourses">Course Management</a></li>
    		<li class="breadcrumb-item"><a href="DisplayAssignment?id=${sessionID}">${url}</a></li>
    		<li class="breadcrumb-item active">Assignments</li>
  		</ol>
	</nav>
	<table class="table table-bordered">
		<thead>
			<tr class="text-center">
				<th>Assignments</th><th>Submissions</th><th>Latest Submission</th>
			</tr>
		</thead>
		<tbody>
		<c:if test="${userInput.assignments != null}">
			<c:forEach items="${userInput.assignments}" var="assignment">
				<tr>
				<td><a href="DisplaySubmission?assignment=${assignment.id}">${assignment.assignmentName}</a></td>
				<td class="text-center">${assignment.numOfSubmissions}</td>
				<c:choose>
					<c:when test="${assignment.numOfSubmissions > 0}">
						<td style="text-align:center">${assignment.date}</td>
					</c:when>
					<c:when test="${assignment.numOfSubmissions < 1}">
						<td style="text-align:center"></td>
					</c:when>
				</c:choose>
				</tr>	
			</c:forEach>
		</c:if>
		</tbody>
	</table>
	<br><a class="h6" href="CreateAssignment?id=${sessionID}">Create Assignment</a>
	<!-- JavaScript Bundle with Popper -->
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>
</html>