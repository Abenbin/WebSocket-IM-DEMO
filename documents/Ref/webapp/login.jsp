<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<base href="${ctx}/">
<title>登录</title>
<meta charset="UTF-8">
</head>
<body>
	<div class="main">
    <form action="login" method="post">
      <div class="login">
        <ul class="box">
          <li style="height: 30px;">
          	<c:choose>
	            <c:when test="${errorMessage==null}">
	            </c:when>
	            <c:otherwise>
	              <p class="error">${errorMessage}</p>
	            </c:otherwise>
	          </c:choose>
          </li>
          <li class="mb"><span>用户名：</span>  <input id="username" name="loginName" class="wid" type="text"></li>
          <li><span>密&nbsp;&nbsp;码：</span> <input id="password" name="password" class="wid" type="password"></li>
          <li class="remember"><input id="remember" type="checkbox" checked>记住密码</li>
        </ul>
      </div>
      <div class="bt">
        <button class="btn button" type="submit">登&nbsp;&nbsp;录</button>
      </div>
    </form>
  </div>
</body>