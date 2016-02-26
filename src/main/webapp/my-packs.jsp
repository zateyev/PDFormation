<%--@elvariable id="user" type="kz.zateyev.pdformation.entity.User"--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

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
    <title>My packs</title>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.2/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="jumbotron-narrow.css"/>
</head>
<body style="zoom: 1;">
<div class="container">
    <div class="header">
        <ul class="nav nav-pills pull-right">
            <li class="active"><a href="#"><span class="glyphicon glyphicon-home"></span> Главная</a></li>
            <li><a href="#">${user.firstName}</a></li>
            <li><a href="create-pack.jsp">Создать пакет</a></li>
            <li>
                <form method="get" action="${pageContext.request.contextPath}/logout">
                    <button class="btn btn-default navbar-btn">Выйти</button>
                </form>
            </li>
        </ul>
        <h1 class="text-muted"><fmt:message key="project.name" /></h1>
    </div>

    <h3>Мои пакеты</h3>
    <table class="table">
        <tr>
            <th>Пакет</th>
            <th></th>
            <th></th>
        </tr>
        <tbody>
        <jsp:useBean id="packs" scope="session" type="java.util.List"/>
        <c:forEach items="${packs}" var="pack">
            <tr>
                <td>${pack.name}</td>
                <td><a href="${pageContext.request.contextPath}/form?packid=${pack.id}">Заполнить</a></td>
                <td><a href="${pageContext.request.contextPath}/remove?packid=${pack.id}">Удалить</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%@include file="footer.jspf" %>
</div>
</body>
</html>