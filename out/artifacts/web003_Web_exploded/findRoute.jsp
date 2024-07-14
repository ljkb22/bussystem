<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.PrintStream" %>
<%@ page import="org.example.Route" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>搜索公交路线</title>
</head>
<body>
<h1>搜索公交路线</h1>
<form action="findRoute" method="get">
    <label for="searchType">搜索类型:</label>
    <select id="searchType" name="searchType">
        <option value="exact">精确搜索</option>
        <option value="fuzzy">模糊搜索</option>
    </select>
    <br>
    <label for="searchValue">搜索值:</label>
    <input type="text" id="searchValue" name="searchValue" required>
    <br>
    <label for="sortBy">排序方式:</label>
    <select id="sortBy" name="sortBy">
        <option value="id">ID</option>
        <option value="low_price">低票价</option>
        <option value="start_time">起始时间</option>
        <option value="interval">运行间隔</option>
    </select>
    <br>
    <input type="submit" value="搜索">
</form>

<!-- 其他循环和输出 -->
<%
    List<Route> routes = (List<Route>) request.getAttribute("routes");
    System.out.println("hello");
    System.out.println(routes);
    if (routes != null && !routes.isEmpty()) {
        out.println("<h2>搜索结果:</h2>");
        for (Route route : routes) {
            out.println("<p>路线ID: " + route.getId() + "</p>");
            out.println("<p>路线名称: " + route.getName() + "</p>");
            out.println("<p>起始时间: " + route.getStartTime() + "</p>");
            out.println("<p>结束时间: " + route.getEndTime() + "</p>");
            out.println("<p>低票价: " + route.getLowPrice() + "</p>");
            out.println("<p>高票价: " + route.getHighPrice() + "</p>");
            out.println("<p>运营公司: " + route.getCompany() + "</p>");
            out.println("<p>运行间隔: " + route.getInterval() + "</p>");
            out.println("<p>途径站点:</p>");
            String[] stations = route.getStations();
            for (String station : stations) {
                out.println("<p>站点: " + station + "</p>");
            }
            out.println("<hr>");
        }
    } else {
        out.println("<h2>未搜索到结果</h2>");
    }
%>
</body>
</html>