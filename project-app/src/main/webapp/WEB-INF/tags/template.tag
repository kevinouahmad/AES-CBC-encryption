<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<jsp:directive.attribute name="head" required="false" fragment="true"/>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title> project-app </title>

		<c:url value="/" var="baseUrl" />
		<c:set value="${fn:length(baseUrl)}" var="baseUrlLen" />
		<c:set var="baseUrl" value="${fn:substring(baseUrl, 0, baseUrlLen - 1)}"/>
		
		<link rel="shortcut icon" type="image/x-icon" href="${baseUrl}/resources/pics/icon.ico"/>
    	<link rel="stylesheet" type="text/css" media="all" href="${baseUrl}/resources/bootstrap/css/bootstrap.css"/>
    	<link rel="stylesheet" type="text/css" media="all" href="${baseUrl}/resources/bootstrap/css/bootstrap-responsive.css"/>
		<link rel="stylesheet" type="text/css" media="all" href="${baseUrl}/resources/style.css"/>
		<script type="text/javascript" src="${baseUrl}/resources/project.js" ></script>
		<jsp:invoke fragment="head"/>
	</head>
	<body>
		<div class="nav">
			<a href="/project-app/"><img class="logo"
				src="${baseUrl}/resources/pics/logo.png" alt="Groupe 9"></a>
		</div>
		<div id="backLayer">
			<div id="main">
				<jsp:doBody />
			</div>
		</div>
		<footer>
			<p>
				Made with <span style="color:#571778; font-size:20px">&hearts;</span> by Groupe 9
			</p>
		</footer>
	</body>
</html>