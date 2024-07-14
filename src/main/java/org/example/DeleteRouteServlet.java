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

@WebServlet("/deleteRoute")
public class DeleteRouteServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String routeId = request.getParameter("routeId");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 检查路线是否存在
            String checkRouteQuery = "SELECT COUNT(*) FROM route WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkRouteQuery)) {
                checkStmt.setInt(1, Integer.parseInt(routeId));
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        request.setAttribute("errorMessage", "该路线不存在！");
                        request.getRequestDispatcher("deleteRoute.jsp").forward(request, response);
                        return;
                    }
                }
            }

            // 删除 relation 表中的相关记录
            String deleteRelationQuery = "DELETE FROM relation WHERE route_id = ?";
            try (PreparedStatement deleteRelationStmt = conn.prepareStatement(deleteRelationQuery)) {
                deleteRelationStmt.setInt(1, Integer.parseInt(routeId));
                deleteRelationStmt.executeUpdate();
            }

            // 删除 route 表中的记录
            String deleteRouteQuery = "DELETE FROM route WHERE id = ?";
            try (PreparedStatement deleteRouteStmt = conn.prepareStatement(deleteRouteQuery)) {
                deleteRouteStmt.setInt(1, Integer.parseInt(routeId));
                deleteRouteStmt.executeUpdate();
            }

            response.sendRedirect("deleteRoute.jsp?success=true");
        } catch (SQLException e) {
            throw new ServletException("Database error occurred", e);
        }
    }
}