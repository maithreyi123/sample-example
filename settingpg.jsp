<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../DigiChatBot.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Settings</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/css/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/css/main.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/css/digi.css">

<!-- <script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script> -->

<script src="${pageContext.request.contextPath}/js/exporting.js"></script>
<script src="${pageContext.request.contextPath}/js/highchart_for3d.js"></script>
<script src="${pageContext.request.contextPath}/js/highcharts-3d.js"></script>
<script src="${pageContext.request.contextPath}/js/export-data.js"></script>


<script
	src="${pageContext.request.contextPath}/vendor/jquery/dist/jquery.js"></script>
<script
	src="${pageContext.request.contextPath}/vendor/angular/angular.js"></script>
<script
	src="${pageContext.request.contextPath}/vendor/angular-ui-router/release/angular-ui-router.js"></script>
<script
	src="${pageContext.request.contextPath}/vendor/angular-route/angular-route.js"></script>
<script
	src="${pageContext.request.contextPath}/vendor/angular-ui-bootstrap-bower/ui-bootstrap.js"></script>
<script
	src="${pageContext.request.contextPath}/vendor/angular-ui-bootstrap-bower/ui-bootstrap-tpls.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/vendor/bootstrap/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/app/app.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<!-- <script type="text/javascript">
       // Function to start seetest URL in New Tab
      function runSeeTest() {
    	  
		window.open('https://capgemini.pcloudy.com', '_blank');
	}      
</script> -->

<!-- <script>
	function formValidation() {

		if (document.getElementById("pcluname").value == ""
				|| document.getElementById("pclkey").value == ""
				|| document.getElementById("pclpass").value == "");
				
				
	}
</script> -->
<script type="text/javascript">




</script>


<title>Insert title here</title>
</head>
<body>



	<!-- Header and Navigation Bar -->

	<jsp:include page="./common/logo.jsp"></jsp:include>
	<div class="marginTop65px"></div>
	<div class="row borderBottom">
		<div class="col-md-4 col-lg-4 marginTop15px"></div>
		<div class="col-md-3 col-lg-4"></div>
		<div class="col-md-5 col-lg-4 marginTop15px">

			<a href="../../mobileLab/mobileLab.jsp"><button
					class="btn btn-primary sideButton">Help</button></a>
			<%-- <button type="button" class="btn btn-primary pull-right"
				onclick="runSeeTest()" title="Start Testing">
				Start Testing <span class="glyphicon glyphicon-plus"></span>
			</button>
			<a href="../../mobileLab/downloadExcelUtility"><button
					type="button" class="btn btn-primary sideButton">
					Download TestPlan <img
						src="${pageContext.request.contextPath}/styles/images/download.png"
						height="20" width="20" title="Download TestPlan">
				</button> </a> --%>
		</div>

	</div>


	<div class="marginTop50px"></div>
	<div class="container">
		<h1 class="text-center addRunHeading greyTextColor">Settings</h1>
		<div class="container">
			<div class="">
				<div class="col-sm-2 col-md-2"></div>
				<div class="col-sm-8 col-md-8">
					<div class="marginTop15px"></div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<span class="panel-title"><strong>Credentials</strong></span>
						</div>
						<div class="panel-body">
							<div class="marginTop15px"></div>
							<form action="fetchapi" method="POST">
								<%-- <form:form class="form-horizontal"
								action="${pageContext.request.contextPath}/console/addImtaRun"
								method="POST" id="addImtaRun" modelAttribute="newRun"> --%>

								<%--  <c:forEach items="${users}" var="users"> --%>
								<div class="form-group">
									<label>pCloudy Username :</label> <input name="pcloudyuname"
										placeholder="Enter pCloudy Username" /><a href="./editSetting"></a>
									<!-- <button>edit</button> -->
									<% String pcluname=request.getParameter("pcluname"); %><input
										type="button" name="edit" value="Edit"
										style="background-color: green; font-weight: bold; color: white;"><a href="./editSetting"></a>
								</div>
								<div class="form-group">
									<label>pCloudy API :</label> <input name="pcloudykey"
										placeholder="Enter pCloudy API key" />
										<% String pclkey=request.getParameter("pclkey"); %>
										 <input type="button" name="edit" value="Edit"
										style="background-color: green; font-weight: bold; color: white;">
								</div>

								<div class="form-group">
									<label>pCloudy password :</label> <input name="pcloudypass"
										placeholder="Enter pCloudy password" /><% String pclpass=request.getParameter("pclpass"); %><input type="button"
										name="edit" value="Edit"
										style="background-color: green; font-weight: bold; color: white;">

								</div>
								 <%--  </c:forEach>  --%>

								<div class="marginTop50px"></div>
								<div class="form-group">
									<div class="col-sm-3 col-sm-offset-5">
										<input type="submit" class="btn btn-success btnCenter">
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="marginTop15px"></div>
					<!-- </div> -->
				</div>
				<div class="col-sm-2 col-md-2"></div>
			</div>
		</div>
		<%-- <div>
		<!-- Footer -->
		<jsp:include page="common/footer.jsp"></jsp:include>
	</div> --%>
</body>
</html>
