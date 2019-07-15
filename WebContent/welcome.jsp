<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Name Management</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="Styling.css">
<script type="text/javascript" src="AJAXScript.js"></script>
</head>
<body class="text-center" onload="displayContact()">
	<div class="container">
		<div class="container">
			<h1 class="text-right text-info bg-success" style="font-size: 75vp">Name
				Management</h1>
		</div>
		<div class="container"
			style="width: 600px, height:1030px">
			<table class="table table-hover  table-responsive">
				<tbody id="contactList">
				</tbody>
			</table>
		</div>
		<div class="container panel-group">
			<div class="panel">
				<div class="panel-heading text-right">
					<a href="#addname" class="btn btn-info" data-toggle="collapse">
						Create a new Name </a>
				</div>
				<div class="panel-collapse collapse fade" id="addname">
					<div class="form-format">
						<input type="email" id="login" class="form-control"
							placeholder="Enter e-mail" required autofocus> <input
							type="text" id="name" class="form-control"
							placeholder="Enter Name" required>
					</div>
					<div class="form-format text-right" id="phoneBody">
						<input type="text" class="form-control"
							placeholder="Enter mobile number" required>
					</div>
					<button class="form-format btn btn-block btn-info"
						onclick="addMobileTextBox()">Add Another number</button>
					<div class="form-format text-right" id="mailBody">
						<input type="email" class="form-control"
							placeholder="Enter Alternate e-mail" required>
					</div>
					<button class="form-format btn btn-block btn-info"
						onclick="addEMailTextBox()">Add Another E-Mail</button>

					<div class="form-format">
						<input type="text" id="house_no" class="form-control"
							placeholder="Enter house number" required> <input
							type="text" id="street_name" class="form-control"
							placeholder="Enter street name" required> <input
							type="text" id="area" class="form-control"
							placeholder="Enter area" required> <input type="text"
							id="city" class="form-control" placeholder="Enter city" required>
						<input type="text" id="state" class="form-control"
							placeholder="Enter state" required> <input type="text"
							id="zipcode" class="form-control" placeholder="Enter zipcode"
							required>
					</div>
					<div class="form-format text-center">
						<button class="btn btn-primary btn-cancel" type="submit"
							data-toggle="collapse" data-target="#addname"
							onclick="addContact()">Add</button>
						<button class="btn btn-default btn-cancel " type="reset"
							data-toggle="collapse" data-target="#addname"
							onclick="clearInformation()">Cancel</button>
					</div>
					<p id="result"></p>
				</div>
			</div>
		</div>
		<footer class="page-footer font-small">
			<div class="footer-copyright text-center py-3">
				&copy; 2018 Copyright: <strong>Jeeva Sudhegan</strong>
			</div>
		</footer>
	</div>
</body>
</html>
