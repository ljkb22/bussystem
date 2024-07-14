package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/modifyRoute")
public class ModifyRouteServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String lowPrice = request.getParameter("low_price");
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");
        String interval = request.getParameter("interval");
        String company = request.getParameter("company");
        String highPrice = request.getParameter("high_price");
        String[] stations = request.getParameterValues("stations");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 更新路线信息
            String updateRouteQuery = "UPDATE route SET name = ?, low_price = ?, start_time = ?, end_time = ?, `interval` = ?, company = ?, high_price = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateRouteQuery)) {
                stmt.setString(1, name);
                stmt.setDouble(2, Double.parseDouble(lowPrice));
                stmt.setString(3, startTime);
                stmt.setString(4, endTime);
                stmt.setString(5, interval);
                stmt.setString(6, company);
                stmt.setDouble(7, Double.parseDouble(highPrice));
                stmt.setInt(8, Integer.parseInt(id));
                stmt.executeUpdate();
            }

            // 更新站点信息
            // 先删除 relation 表中的旧站点记录
            String deleteRelationsQuery = "DELETE FROM relation WHERE route_id = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteRelationsQuery)) {
                deleteStmt.setInt(1, Integer.parseInt(id));
                deleteStmt.executeUpdate();
            }

            // 插入新的站点记录到 relation 表中
            String insertRelationQuery = "INSERT INTO relation (route_id, station_id) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertRelationQuery)) {
                for (String stationName : stations) {
                    // 查找站点名对应的 station_id
                    String findStationIdQuery = "SELECT id FROM station WHERE name = ?";
                    try (PreparedStatement findStmt = conn.prepareStatement(findStationIdQuery)) {
                        findStmt.setString(1, stationName);
                        try (ResultSet rs = findStmt.executeQuery()) {
                            if (rs.next()) {
                                int stationId = rs.getInt("id");
                                insertStmt.setInt(1, Integer.parseInt(id));
                                insertStmt.setInt(2, stationId);
                                insertStmt.executeUpdate();
                            }
                        }
                    }
                }
            }

            response.sendRedirect("searchRoute.jsp?success=true");
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}