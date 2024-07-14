package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/findRoute")
public class FindRouteServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");
        String sortBy = request.getParameter("sortBy");

        List<Route> routes = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM route WHERE ";
            String orderClause = " ORDER BY " + sortBy;

            if ("exact".equals(searchType)) {
                query += "id = ? OR name = ?";
            } else {
                query += "name LIKE ?";
            }

            query += orderClause;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                if ("exact".equals(searchType)) {
                    stmt.setString(1, searchValue);
                    stmt.setString(2, searchValue);
                } else {
                    stmt.setString(1, "%" + searchValue + "%");
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Route route = new Route();
                        route.setId(rs.getInt("id"));
                        route.setName(rs.getString("name"));
                        route.setStartTime(rs.getTime("start_time").toString()); // 转换为字符串
                        route.setEndTime(rs.getTime("end_time").toString()); // 转换为字符串
                        route.setLowPrice(rs.getDouble("low_price"));
                        route.setHighPrice(rs.getDouble("high_price"));
                        route.setCompany(rs.getString("company"));
                        route.setInterval(rs.getString("interval"));

                        // 获取途径站点
                        String stationQuery = "SELECT station.name FROM relation "
                                + "JOIN station ON relation.station_id = station.id "
                                + "WHERE relation.route_id = ?";
                        try (PreparedStatement stationStmt = conn.prepareStatement(stationQuery)) {
                            stationStmt.setInt(1, rs.getInt("id"));
                            try (ResultSet stationRs = stationStmt.executeQuery()) {
                                List<String> stations = new ArrayList<>();
                                while (stationRs.next()) {
                                    stations.add(stationRs.getString("name"));
                                }
                                route.setStations(stations.toArray(new String[0])); // 将 List 转换为 String 数组
                            }
                        }

                        routes.add(route);
                    }
                }
            }
            request.setAttribute("routes", routes);
            request.getRequestDispatcher("findRoute.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}
