<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>类目列表</title>
<meta charset="utf-8"/>
<link rel="stylesheet" href="/admin/css/bootstrap.css"/>
	<link rel="stylesheet" href="/admin/layui/css/layui.css"  media="all">
</head>
<body>
<div class="container-fluid">

	<%@include file="header.jsp"%>
	
	<br>
	
	<div>
		<form class="form-inline" method="post" action="typeSave">
			<input type="text" class="layui-input" id="input_name" name="name" placeholder="输入类目名称" required="required" style="width: 500px"><br>
			<input type="submit" class="layui-btn layui-btn-warm" value="添加类目"/>
		</form>
	</div>
	
	<br>

	<table class="layui-table" lay-even="" lay-skin="row">

	<tr>
		<th width="5%">ID</th>
		<th width="10%">名称</th>
		<th width="10%">操作</th>
	</tr>
	
	<c:forEach var="type" items="${typeList}">
         <tr>
         	<td><p>${type.id}</p></td>
         	<td><p>${type.name}</p></td>
			<td>
				<a href="typeEdit?id=${type.id}"><button type="button" class="layui-btn layui-btn-sm"><i class="layui-icon">修改</i></button></a>
				<a href="typeDelete?id=${type.id}"><button type="button" class="layui-btn layui-btn-sm layui-btn-danger"><i class="layui-icon">删除</i></button></a>
<%--				<a class="btn btn-primary" href="typeEdit?id=${type.id}">修改</a>--%>
<%--				<a class="btn btn-danger" href="typeDelete?id=${type.id}">删除</a>--%>
			</td>
       	</tr>
     </c:forEach>
     
</table>

</div>
<script src="/admin/layui/layui.js" charset="utf-8"></script>
</body>
</html>