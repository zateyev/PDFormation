<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Formed document</title>
</head>
<body>
<h2>Документ сформирован</h2>
<jsp:useBean id="fileName" scope="session" type="java.lang.String"/>
<p>Документ <a href="${pageContext.request.contextPath}/download?filename=${fileName}">${fileName}</a> готов к скачиванию</p>
</body>
</html>