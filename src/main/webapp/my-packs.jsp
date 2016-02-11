<%--@elvariable id="user" type="kz.zateyev.pdformation.entity.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>My packs</title>
</head>
<body>
<h3>${user.firstName}</h3>
<ul>
    <jsp:useBean id="packs" scope="session" type="java.util.List"/>
    <c:forEach items="${packs}" var="pack">
            <li><a href="${pageContext.request.contextPath}/form?packid=${pack.id}">Заполнить ${pack.name}</a></li>
        </c:forEach>
    </ul>
    <a href="create-pack.jsp">Создать пакет</a>
    <a href="${pageContext.request.contextPath}/logout">Выйти</a>
</body>
</html>