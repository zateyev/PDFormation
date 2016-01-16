<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>File Upload</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data" >
  <input type="file" name="file" id="file" />
  <input type="submit" />
</form>
</body>
</html>