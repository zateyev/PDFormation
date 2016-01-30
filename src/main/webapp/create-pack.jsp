<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/multipleUpload" enctype="multipart/form-data" >
    <input name="packname" type="text" placeholder="pack name">
    <input type="file" name="files" id="file" multiple="multiple"/>
    <input type="submit"/>
</form>
</body>
</html>
