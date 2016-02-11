<%--@elvariable id="fileName" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Formed document</title>
</head>
<body>
<h2>Документ сформирован</h2>
<jsp:useBean id="pack" scope="session" type="kz.zateyev.pdformation.entity.Pack"/>
<c:forEach items="${pack.documents}" var="document">
    <li><a href="${pageContext.request.contextPath}/download?filename=${document.name}">Скачать ${document.name}</a></li>
</c:forEach>
</body>
</html>