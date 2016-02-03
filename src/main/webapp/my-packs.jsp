<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="language" scope="session" type="java.util.Locale"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>My packs</title>
</head>
<body>
    <ul>
        <jsp:useBean id="packs" scope="session" type="java.util.List"/>
        <c:forEach items="${packs}" var="pack">
            <li><a href="${pageContext.request.contextPath}/formGenerator?packid=${pack.id}">Заполнить $(pack.name)</a></li>
        </c:forEach>
    </ul>
    <a href="${pageContext.request.contextPath}/main">Создать пакет</a>
</body>
</html>