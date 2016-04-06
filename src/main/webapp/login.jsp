<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n" />

<!DOCTYPE html>
<html lang="${language}">
<head>
  <meta charset="UTF-8">
  <title><fmt:message key="login" /></title>
  <link rel="stylesheet" href="style.css"/>
</head>
<body>

<form action="${pageContext.request.contextPath}/login" method="post" class="form">
  <label><fmt:message key="login" /></label>
  <input name="email" type="text" placeholder="email">
  <input name="password" type="password" placeholder="<fmt:message key="password" />">
  <button><fmt:message key="submit" /></button>
</form>
</body>
</html>