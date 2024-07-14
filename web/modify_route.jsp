<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.Route" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改路线</title>
    <script>
        function addStationField() {
            const container = document.getElementById('stationContainer');
            const input = document.createElement('input');
            input.type = 'text';
            input.name = 'stations';
            input.required = true;
            container.appendChild(input);
            container.appendChild(document.createElement('br'));
        }

        function removeStationField() {
            const container = document.getElementById('stationContainer');
            if (container.children.length > 2) { // 至少保留一个站点输入框
                container.removeChild(container.lastChild); // 移除 <br>
                container.removeChild(container.lastChild); // 移除 input
            }
        }
    </script>
</head>
<body>
<div class="container">
    <h1>修改路线</h1>
    <%
        Route route = (Route) request.getAttribute("route");
        if (route != null) {
    %>
    <form action="modifyRoute" method="post">
        <p>找到该路线</p>
        <label for="id">路线ID:</label>
        <input type="number" id="id" name="id" value="<%= route.getId() %>" readonly>
        <br>

        <label for="name">名称:</label>
        <input type="text" id="name" name="name" value="<%= route.getName() %>" required>
        <br>

        <label for="low_price">最低票价:</label>
        <input type="number" step="0.01" id="low_price" name="low_price" value="<%= route.getLowPrice() %>" required>
        <br>

        <label for="start_time">开始时间:</label>
        <input type="time" id="start_time" name="start_time" value="<%= route.getStartTime() %>" required>
        <br>

        <label for="end_time">结束时间:</label>
        <input type="time" id="end_time" name="end_time" value="<%= route.getEndTime() %>" required>
        <br>

        <label for="interval">间隔:</label>
        <input type="text" id="interval" name="interval" value="<%= route.getInterval() %>" required>
        <br>

        <label for="company">公司:</label>
        <input type="text" id="company" name="company" value="<%= route.getCompany() %>" required>
        <br>

        <label for="high_price">最高票价:</label>
        <input type="number" step="0.01" id="high_price" name="high_price" value="<%= route.getHighPrice() %>" required>
        <br>

        <label>站点:</label>
        <div id="stationContainer">
            <%
                String[] stations = route.getStations();
                if (stations != null) {
                    for (String station : stations) {
            %>
            <input type="text" name="stations" value="<%= station %>" required>
            <br>
            <%
                    }
                }
            %>
        </div>
        <button type="button" onclick="addStationField()">增加站点</button>
        <button type="button" onclick="removeStationField()">删除站点</button>
        <br><br>
        <button type="submit" class="button">修改路线</button>
    </form>
    <%
    } else {
    %>
    <p>未找到该路线，请重新搜索。</p>
    <%
        }
    %>
</div>
</body>
</html>