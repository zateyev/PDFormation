<%--@elvariable id="user" type="kz.zateyev.pdformation.entity.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create pack</title>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.2/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="jumbotron-narrow.css"/>
</head>
<body style="zoom: 1;">
<div class="container">
    <div class="header">
        <ul class="nav nav-pills pull-right">
            <li><a href="my-packs.jsp"><span class="glyphicon glyphicon-home"></span> Главная</a></li>
            <li><a href="#">${user.firstName}</a></li>
            <li class="active"><a href="create-pack.jsp">Создать пакет</a></li>
            <li>
                <form method="get" action="${pageContext.request.contextPath}/logout">
                    <button class="btn btn-default navbar-btn">Выйти</button>
                </form>
            </li>
        </ul>
        <h1 class="text-muted"><fmt:message key="project.name" /></h1>
    </div>

    <div class="jumbotron clearfix">
        <form method="POST" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" >
            <input name="packname" type="text" placeholder="pack name">
            <input type="file" name="files" id="file" multiple="multiple"/>
            <button class="btn btn-lg btn-success pull-right"><fmt:message key="pack.submit" /></button>
        </form>
    </div>

    <%@include file="footer.jspf" %>
</div>
</body>
</html>
