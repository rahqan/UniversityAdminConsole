package com.aurionpro.dao;

import com.aurionpro.database.Database;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeesDAO {

    public Map<String, Object> getStudentCourseFeeRecord(int studentId, int courseId) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        String sql = "SELECT fee_locked_amount, total_paid_amount, is_fully_paid FROM student_course_fees WHERE student_id = ? AND course_id = ?";
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, studentId);
        stmt.setInt(2, courseId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            record.put("fee_locked_amount", rs.getBigDecimal("fee_locked_amount"));
            record.put("total_paid_amount", rs.getBigDecimal("total_paid_amount"));
            record.put("is_fully_paid", rs.getBoolean("is_fully_paid"));
            return record;
        }
        return null;
    }

    public void updateStudentPayment(int studentId, int courseId, BigDecimal newTotalPaid, boolean isFullyPaid) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        String sql = "UPDATE student_course_fees SET total_paid_amount = ?, is_fully_paid = ?, updated_at = CURRENT_TIMESTAMP WHERE student_id = ? AND course_id = ?";
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setBigDecimal(1, newTotalPaid);
        stmt.setBoolean(2, isFullyPaid);
        stmt.setInt(3, studentId);
        stmt.setInt(4, courseId);
        stmt.executeUpdate();
    }

    public List<Map<String, Object>> getAllRemainingFees() throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        List<Map<String, Object>> result = new ArrayList<>();
        
        String sql = """
            SELECT 
                scf.student_id,
                scf.course_id,
                s.name as student_name,
                c.name as course_name,
                scf.fee_locked_amount as original_fee,
                scf.total_paid_amount as paid_amount,
                (scf.fee_locked_amount - scf.total_paid_amount) as remaining_amount,
                scf.is_fully_paid
            FROM student_course_fees scf
            JOIN student s ON scf.student_id = s.student_id
            JOIN course c ON scf.course_id = c.course_id
            ORDER BY s.name, c.name
            """;
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            row.put("student_id", rs.getInt("student_id"));
            row.put("course_id", rs.getInt("course_id"));
            row.put("student_name", rs.getString("student_name"));
            row.put("course_name", rs.getString("course_name"));
            row.put("original_fee", rs.getBigDecimal("original_fee"));
            row.put("paid_amount", rs.getBigDecimal("paid_amount"));
            row.put("remaining_amount", rs.getBigDecimal("remaining_amount"));
            row.put("is_fully_paid", rs.getBoolean("is_fully_paid"));
            result.add(row);
        }
        
        return result;
    }

    public int getStudentFeeRecordCount(int studentId) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM student_course_fees WHERE student_id = ?";
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, studentId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public List<Map<String, Object>> getStudentRemainingFees(int studentId) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        List<Map<String, Object>> result = new ArrayList<>();
        
        String sql = """
            SELECT 
                scf.student_id,
                scf.course_id,
                s.name as student_name,
                c.name as course_name,
                scf.fee_locked_amount as original_fee,
                scf.total_paid_amount as paid_amount,
                (scf.fee_locked_amount - scf.total_paid_amount) as remaining_amount,
                scf.is_fully_paid
            FROM student_course_fees scf
            JOIN student s ON scf.student_id = s.student_id
            JOIN course c ON scf.course_id = c.course_id
            WHERE scf.student_id = ?
            ORDER BY c.name
            """;
        
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, studentId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            row.put("student_id", rs.getInt("student_id"));
            row.put("course_id", rs.getInt("course_id"));
            row.put("student_name", rs.getString("student_name"));
            row.put("course_name", rs.getString("course_name"));
            row.put("original_fee", rs.getBigDecimal("original_fee"));
            row.put("paid_amount", rs.getBigDecimal("paid_amount"));
            row.put("remaining_amount", rs.getBigDecimal("remaining_amount"));
            row.put("is_fully_paid", rs.getBoolean("is_fully_paid"));
            result.add(row);
        }
        
        return result;
    }
}
