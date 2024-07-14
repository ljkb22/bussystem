<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>删除公交路线</title>
</head>
<body>
<h1>删除公交路线</h1>
<form action="deleteRoute" method="post">
    <label for="routeId">路线ID:</label>
    <input type="text" id="routeId" name="routeId" required>
    <input type="submit" value="删除">
</form>
<script type="text/javascript">
    var errorMessage = '<%= (String) request.getAttribute("errorMessage") %>';
    if (errorMessage !== "") {
        alert(errorMessage);
    }
    else {
        alert("删除成功！");
    }

</script>
</body>
</html>