<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<title>Display Submissions</title>
</head>
<body class="container">
	<nav aria-label="breadcrumb" style="font-size:22px; padding-top:25px; --bs-breadcrumb-divider: '>'">
  		<ol class="breadcrumb">
    		<li class="breadcrumb-item"><a href="DisplayCourses">Course Management</a></li>
    		<li class="breadcrumb-item"><a href="DisplayAssignment?id=${courseID}">${coursetURL}</a></li>
    		<li class="breadcrumb-item"><a href="DisplaySubmission?assignment=${assignmentID}">${assignmentURL}</a></li>
    		<li class="breadcrumb-item active">Submissions</li>
  		</ol>
	</nav>
	<table class="table table-bordered">
		<thead>
			<tr class="text-center">
				<th>Student Name</th><th>Answer</th><th>Submitted Date</th>
			</tr>
		</thead>  
		<tbody>
			<c:if test="${submissions != null}">
				<c:forEach items="${submissions}" var="submission">
					<tr>
						<td style="text-align:center; width:20%">${submission.studentName}</td>
						<td>${submission.answer}</td>
						<td style="text-align:center; width:20%">${submission.date}</td>
					</tr>	
				</c:forEach>
			</c:if>	
		</tbody>
	</table>
	<br><a class="h6" href="CreateSubmission?assignment=${assignmentID}">Create Submission</a>
</body>
</html>