package org.example;

import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/searchRoute")
public class SearchRouteServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String routeName = request.getParameter("route_name");

        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败：");
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM route WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, routeName);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Route route = new Route();
                    route.setId(rs.getInt("id"));
                    route.setName(rs.getString("name"));
                    route.setStartTime(rs.getString("start_time"));
                    route.setEndTime(rs.getString("end_time"));
                    route.setLowPrice(rs.getDouble("low_price"));
                    route.setHighPrice(rs.getDouble("high_price"));
                    route.setCompany(rs.getString("company"));
                    route.setInterval(rs.getString("interval"));

                    // 查询站点信息
                    String stationQuery = "SELECT s.name FROM station s INNER JOIN relation r ON s.id = r.station_id WHERE r.route_id = ?";
                    try (PreparedStatement stationStmt = conn.prepareStatement(stationQuery)) {
                        stationStmt.setInt(1, route.getId());
                        ResultSet stationRs = stationStmt.executeQuery();
                        List<String> stations = new ArrayList<>();
                        while (stationRs.next()) {
                            stations.add(stationRs.getString("name"));
                        }
                        route.setStations(stations.toArray(new String[0]));
                    }

                    request.setAttribute("route", route);
                    request.getRequestDispatcher("modify_route.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "未找到该路线");
                    request.getRequestDispatcher("searchRoute.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}