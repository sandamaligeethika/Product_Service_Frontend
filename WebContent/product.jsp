<%@page import="com.dao.ProductDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.2.1.min.js"></script>
		<script src="Components/product.js"></script>
</head>
<body>
<div class="container">
<div class="row">
<div class="col-6">

<h1>Items Management V10.1</h1>

<form id="formItem" name="formItem">
Product ID:
<input id="productId" name="productId" type="text"
class="form-control form-control-sm">
<br> Product name:
<input id="productName" name="productName" type="text"
class="form-control form-control-sm">
<br> Date:
<input id="productDate" name="productDate" type="text"
class="form-control form-control-sm">
<br> Price:
<input id="productPrice" name="productPrice" type="text"
class="form-control form-control-sm">
<br> Researcher Id:
<input id="researcherId" name="researcherId" type="text"
class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save"
class="btn btn-primary">
<input type="hidden" id="hidItemIDSave"
name="hidItemIDSave" value="">
</form>


<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divItemsGrid">

<%
ProductDAOImpl product = new ProductDAOImpl();
out.print(product.listProducts());
%>
</div>
</div>
</div>
 </div>

</body>
</html>