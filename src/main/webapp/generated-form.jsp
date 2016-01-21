<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Form to fill template</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/formGenerator" method="post" class="form">
    <jsp:useBean id="tags" scope="request" type="java.util.Set"/>
    <c:forEach items="${tags}" var="tag">
        <p>
            <label>${tag.name}</label>
            <input name="tagName" type="text" placeholder="${tag.name}">
        </p>
    </c:forEach>
  <button>Submit</button>
</form>
</body>
</html>