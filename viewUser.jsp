<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View User</title>
<link rel="stylesheet" href="../../styles/css/bootstrap.css">
<link rel="stylesheet" href="../../styles/css/main.css">
<link rel="stylesheet" href="../../styles/css/digi.css">
<script type="text/javascript">
	function speak(msg) {
		var msg = new SpeechSynthesisUtterance(msg);
		window.speechSynthesis.speak(msg);
	}
</script>
<script type="text/javascript">
	
</script>
<script type="text/javascript">
	function editUser(userName) {

		window.location = './beforeEditUser?userName=' + userName;
	}

	function deleteUser(userid) {
		//alert(userid);
		var data1 = {
			"userId" : userid
		}
		$.ajax({
			method : 'GET',
			contentType : "application/json",
			url : "./deleteUser",
			data : data1,
			dataType : 'json',
			success : function(data) {

				if (data.result == "success") {
					alert("User Deletion Successful");
					setTimeout(function() {
						window.location = './viewUser'
					}, 1000);

				}

				else if (data.result == "failure") {
					alert("User Deletion Failed");
					setTimeout(function() {
						window.location = './viewUser'
					}, 1000);
				}
			},
			error : function(e) {
				alert(e);

			}
		});
	}
</script>
<script
	src="${pageContext.request.contextPath}/vendor/jquery/dist/jquery.js"></script>
<script
	src="${pageContext.request.contextPath}/vendor/angular-ui-bootstrap-bower/ui-bootstrap.js"></script>
<script
	src="${pageContext.request.contextPath}/vendor/angular-ui-bootstrap-bower/ui-bootstrap-tpls.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/vendor/bootstrap/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/app/app.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>


</head>
<body>
	<jsp:include page="../../../common/logo.jsp"></jsp:include>

	<div class="marginTop50px"></div>

	<!-- <div class="page-content-wrap"> -->

	<!-- START WIDGETS -->
	<div class="row">


		<div style="height: 100%;">
			<!-- 	<div tabindex="0"> -->
			<div id="mCSB_1_container" class="mCSB_container"
				style="top: 0px; left: 0px;" dir="ltr">

				<!-- START X-NAVIGATION -->
				<%-- 	<ul class="x-navigation">

							<li class="xn-openable "><a href="#"><span
									class="fa fa-files-o"></span> <span class="xn-text">Lab</span></a>
								<ul>
								
									<li ><a
										href="../../mobileLab/deviceSelectionMatrix/showLabDetails"><span
											class="fa fa-image"></span>Inventory</a></li>

									<c:if test="${sessionScope.rolename == 'ADMIN'}">
										<li><a
											href="../../mobileLab/deviceSelectionMatrix/addDeviceDeatils"><span
												class="fa fa-user"></span>Add Device</a></li>

										<li><a
											href="../../mobileLab/deviceSelectionMatrix/bulkDeviceDetails"><span
												class="fa fa-users"></span>Excel Import</a></li>

									</c:if>
									<li><a
										href="../../mobileLab/deviceSelectionMatrix/availabilityDetails"><span
											class="fa fa-comments"></span> Availability</a></li>

								</ul></li>

							<li class="xn-openable"><a href="#"><span
									class="fa fa-file-text-o"></span> <span class="xn-text">Bookings</span></a>
								<ul>
									<li><a
										href="../../mobileLab/deviceSelectionMatrix/searchNBookPage">Search
											& Book</a></li>
									<c:if test="${sessionScope.rolename == 'ADMIN'}">
										<li><a
											href="../../mobileLab/deviceSelectionMatrix/adminShowDeviceRequests">Approve/Reject</a></li>
									</c:if>
									<c:if test="${sessionScope.rolename != 'ADMIN'}">
									<li><a
										href="../../mobileLab/deviceSelectionMatrix/requestedBookings">requested
											Bookings</a></li>
											</c:if>
									<li><a
										href="../../mobileLab/deviceSelectionMatrix/allRequests">All
											Requests</a></li>

								</ul></li>

							<li class="xn-openable"><a href="#"><span
									class="fa fa-cogs"></span> <span class="xn-text">Dashboard</span></a>
								<ul>

									<li><a
										href="../../mobileLab/deviceSelectionMatrix/dashboard"><span
											class="fa fa-random"></span> Dashboard View</a></li>
								</ul></li>
							<c:if test="${sessionScope.rolename == 'ADMIN'}">
								<li class="xn-openable active"><a href="#"><span
										class="fa fa-pencil"></span> <span class="xn-text">Users</span></a>
									<ul>
										<li class="active"><a
											href="../../mobileLab/deviceSelectionMatrix/viewUser"><span
												class="fa fa-tasks"></span>View User</a></li>
										<li><a
											href="../../mobileLab/deviceSelectionMatrix/beforeAddUser"><span
												class="fa fa-user"></span>Add User</a></li>
									</ul></li>
								<li class="xn-openable"><a href="#"><span
										class="fa fa-pencil"></span> <span class="xn-text">Projects</span></a>
									<ul>
										<li><a
											href="../../mobileLab/deviceSelectionMatrix/viewProjects"><span
												class="fa fa-tasks"></span>View Projects</a></li>
										<li><a
											href="../../mobileLab/deviceSelectionMatrix/beforeAddProject"><span
												class="fa fa-user"></span>Add Project</a></li>
									</ul></li>

							</c:if>
							<li class="xn-openable"><a href="#"><span
									class="fa fa-table"></span> <span class="xn-text">Reports</span></a>
								<ul>
									<li><a href="../../mobileLab/deviceSelectionMatrix/report"><span
											class="fa fa-align-justify"></span>Usage report</a></li>

								</ul></li>

						</ul>
						<!-- END X-NAVIGATION --> --%>
			</div>



			<div id="mCSB_1_scrollbar_vertical"
				class="mCSB_scrollTools mCSB_1_scrollbar mCS-light mCSB_scrollTools_vertical"
				style="display: block;">
				<div class="mCSB_draggerContainer">
					<div id="mCSB_1_dragger_vertical" class="mCSB_dragger"
						style="position: absolute; min-height: 30px; display: block; height: 113px; max-height: 286px; top: 0px;"
						oncontextmenu="return false;">
						<div class="mCSB_dragger_bar" style="line-height: 30px;"></div>
					</div>
					<div class="mCSB_draggerRail"></div>
				</div>
			</div>
		</div>
	</div>


	<div class="container">

		<h1 id="viewUserHeading"
			class="text-center addRunHeading greyTextColor">View Users</h1>
	</div>


	<div class="container">
		<div class="">
			<div class="col-sm-1 col-md-1"></div>
			<div class="col-sm-10 col-md-10">

				<div class="marginTop15px"></div>
				<div class="panel panel-info">
					<div class="panel-heading" id="viewUserHeading2">
						<span class="panel-title"><strong>View Users</strong></span>
					</div>

					<div class="panel-body">
						<div class="marginTop15px"></div>
						<c:if test="${not empty message }">
							<div class="row">
								<div class="alert alert-success alert-dismissible" role="alert">
									<button type="button" class="close" data-dismiss="alert">
										<span aria-hidden="true">�</span><span class="sr-only">Close</span>
									</button>
									${message}

								</div>
							</div>
						</c:if>

						<div class="marginTop15px"></div>

						<div id="viewUserTable">
							<div class="marginTop15px"></div>
							<c:if test="${ not empty users}">
								<table class="table table-striped table-bordered table-hover"
									id="usersTable">

									<thead>
										<tr>
											<th>Edit</th>
											<th>Delete</th>
											<th>First Name</th>
											<th>Last Name</th>
											<th>Username</th>
											<th>Email</th>
											<th>Phone Number</th>
											<th>Role Name</th>

										</tr>

									</thead>

									<tbody>
										<c:forEach items="${users}" var="users">
											<tr>
												<td><a href="./editUser?userid=${users.userid}"><input
														type="button" class="btn btn-primary" value="Edit" /></a></td>
												<td><a href="./deleteUser?userid=${users.userid}"><input
														type="button" class="btn btn-danger" value="Delete"></a></td>
												<td>${users.firstName}</td>
												<td>${users.lastName}</td>
												<td>${users.userName}</td>
												<td>${users.email}</td>
												<td>${users.phoneNumber}</td>
												<td>${users.rolename}</td>

											</tr>
										</c:forEach>
									</tbody>

								</table>
							</c:if>
							<c:if test="${ empty users}">
								<div class="alert alert-danger alert-dismissible" role="alert">
									<button type="button" class="close" data-dismiss="alert">
										<span aria-hidden="true">�</span><span class="sr-only">Close</span>
									</button>
									No Users ...
								</div>
							</c:if>
						</div>

					</div>
				</div>

			</div>

		</div>
	</div>

	<%-- <div class="footer">
		<jsp:include page="../../../common/footer.jsp"></jsp:include>
	</div>
 --%>

</body>
</html>