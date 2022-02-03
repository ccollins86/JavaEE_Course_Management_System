<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<title>Create Submissions</title>
</head>
<body class="container">
	<nav aria-label="breadcrumb" style="font-size:22px; padding-top:25px; --bs-breadcrumb-divider: '>'">
  		<ol class="breadcrumb">
    		<li class="breadcrumb-item"><a href="DisplayCourses">Course Management</a></li>
    		<li class="breadcrumb-item"><a href="DisplayAssignment?id=${courseID}">${coursetURL}</a></li>
    		<li class="breadcrumb-item"><a href="DisplaySubmission?assignment=${assignmentID}">${assignmentURL}</a></li>
    		<li class="breadcrumb-item active">Create Submission</li>
  		</ol>
	</nav>
	<form action="CreateSubmission?assignmentID=${assignmentID}&assignmentName=${assignmentURL}" method="POST">
		<table class="table table-bordered" style="width:50%">
			<tr>
				<td class="fw-bold" style="width:25%; vertical-align:middle; text-align:center">Student Name:</td>
				<td><input class="form-control" type="text" name="name"/></td>
			</tr>
			<tr>
				<td class="fw-bold" style="width:25%; vertical-align:middle; text-align:center">Answer:</td>
				<td><textarea class="form-control" name="answer" rows="5"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit" class="btn btn-primary">Create</button></td>
			</tr>
		</table>
	</form>
	<!-- JavaScript Bundle with Popper -->
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>
</html>