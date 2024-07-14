<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加路线</title>
    <script>
        function validateForm() {
            let filledStations = 0;
            for (let i = 1; i <= 36; i++) {
                const station = document.querySelector(`[name="station${i}"]`).value.trim();
                if (station !== "") {
                    filledStations++;
                }
            }
            if (filledStations < 2) {
                alert("请至少填写两个站点");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="container">
    <h1>添加路线</h1>
    <form action="addRoute" method="post" onsubmit="return validateForm()">
        <table border="1">
            <tr>
                <th>名称</th>
                <th>最低票价</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>间隔</th>
                <th>公司</th>
                <th>最高票价</th>
            </tr>
            <tr>
                <td><input type="text" name="name" required></td>
                <td><input type="number" step="0.01" name="low_price" required></td>
                <td><input type="time" name="start_time" required></td>
                <td><input type="time" name="end_time" required></td>
                <td><input type="text" name="interval" required></td>
                <td><input type="text" name="company" required></td>
                <td><input type="number" step="0.01" name="high_price" required></td>
            </tr>
        </table>
        <h3>途径站点</h3>
        <table border="1">
            <tbody>
            <!-- 6x6 grid for station names -->
            <% for (int i = 1; i <= 6; i++) { %>
            <tr>
                <% for (int j = 1; j <= 6; j++) { %>
                <td><input type="text" name="station<%= (i-1)*6 + j %>"></td>
                <% } %>
            </tr>
            <% } %>
            </tbody>
        </table>
        <button type="submit" class="button">添加路线</button>
    </form>
</div>
</body>
</html>
