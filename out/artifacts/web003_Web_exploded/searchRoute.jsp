<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改路线</title>
</head>
<body>
<div class="container">
    <h1>修改路线</h1>
    <form action="searchRoute" method="post">
        <label for="route_name">路线名称:</label>
        <input type="text" id="route_name" name="route_name" required>
        <button type="submit" class="button">搜索</button>
    </form>
    <%
        String errorMessage = (String) request.getAttribute("error");
        if (errorMessage != null) {
    %>
    <p style="color: red;"><%= errorMessage %></p>
    <%
        }
    %>
</div>
</body>
</html>