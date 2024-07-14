package org.example;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/addRoute")
public class AddRouteServlet extends HttpServlet {
    public static int getStationIdByName(Connection conn, String stationName) throws SQLException {
        String query = "SELECT id FROM Station WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, stationName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1;
                }
            }
        }
    }
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        double lowPrice = Double.parseDouble(request.getParameter("low_price"));
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");
        String interval = request.getParameter("interval");
        String company = request.getParameter("company");
        double highPrice = Double.parseDouble(request.getParameter("high_price"));
        String updateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String[] stations = new String[36];
        int filledStations = 0;
        for (int i = 1; i <= 36; i++) {
            stations[i - 1] = request.getParameter("station" + i);
            if (stations[i - 1] != null && !stations[i - 1].trim().isEmpty()) {
                filledStations++;
            }
        }

        if (filledStations < 2) {
            response.getWriter().write("请至少填写两个站点");
            return;
        }
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功！");
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败：" );
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            int startStationId = getStationIdByName(conn, stations[0]);
            int endStationId = getStationIdByName(conn, stations[filledStations - 1]);

            if (startStationId == -1 || endStationId == -1) {
                response.getWriter().write("起始站或终点站不存在");
                System.out.println("起始站或终点站不存在");
                return;
            }

            String insertRouteQuery = "INSERT INTO Route (name, low_price, high_price, start_time, end_time, update_time, `interval`, start_station_id, end_station_id, company) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertRouteQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.setDouble(2, lowPrice);
                stmt.setDouble(3, highPrice);
                stmt.setString(4, startTime);
                stmt.setString(5, endTime);
                stmt.setString(6, updateTime);
                stmt.setString(7, interval);
                stmt.setInt(8, startStationId);
                stmt.setInt(9, endStationId);
                stmt.setString(10, company);
                stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int routeId = generatedKeys.getInt(1);

                        String insertRelationQuery = "INSERT INTO Relation (route_id, station_id, position) VALUES (?, ?, ?)";
                        try (PreparedStatement relationStmt = conn.prepareStatement(insertRelationQuery)) {
                            int position = 1;
                            for (String station : stations) {
                                if (station != null && !station.trim().isEmpty()) {
                                    int stationId = getStationIdByName(conn, station);
                                    if (stationId != -1) {
                                        relationStmt.setInt(1, routeId);
                                        relationStmt.setInt(2, stationId);
                                        relationStmt.setInt(3, position++);
                                        relationStmt.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("路线添加成功");
            System.out.println("路线添加成功");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("数据库错误: " + e.getMessage());
        }
    }
}
