package com.aurionpro.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.aurionpro.dao.FeesDAO;
import com.aurionpro.database.Database;

public class FeesService {
    
    private CourseService courseService;
    private FeesDAO feesDAO;
    
    public FeesService() throws SQLException {
        this.courseService = new CourseService();
        this.feesDAO = new FeesDAO();
    }

    public String payFees(int studentId, int courseId, BigDecimal amount) throws SQLException {
        // Business Logic: Validate amount
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }

        // Get fee record from DAO
        Map<String, Object> record = feesDAO.getStudentCourseFeeRecord(studentId, courseId);
        
        if (record == null) {
            throw new RuntimeException("No fee record found for student ID: " + studentId + " and course ID: " + courseId);
        }

        // Business Logic: Extract values and perform validations
        BigDecimal feeLockedAmount = (BigDecimal) record.get("fee_locked_amount");
        BigDecimal totalPaidAmount = (BigDecimal) record.get("total_paid_amount");
        Boolean isFullyPaid = (Boolean) record.get("is_fully_paid");

        // Business Logic: Check if already fully paid
        if (isFullyPaid) {
            throw new RuntimeException("Fees already fully paid for this course");
        }

        BigDecimal remainingAmount = feeLockedAmount.subtract(totalPaidAmount);

        // Business Logic: Reject overpayment
        if (amount.compareTo(remainingAmount) > 0) {
            throw new RuntimeException("Payment amount (" + amount + ") exceeds remaining amount (" + remainingAmount + ")");
        }

        // Business Logic: Calculate new values
        BigDecimal newTotalPaid = totalPaidAmount.add(amount);
        boolean isNowFullyPaid = newTotalPaid.compareTo(feeLockedAmount) >= 0;

        // Update payment through DAO
        feesDAO.updateStudentPayment(studentId, courseId, newTotalPaid, isNowFullyPaid);

        // Business Logic: Return appropriate message
        if (isNowFullyPaid) {
            return "Payment successful! Fees fully paid for this course.";
        } else {
            BigDecimal newRemaining = feeLockedAmount.subtract(newTotalPaid);
            return "Payment successful! Remaining amount: " + newRemaining;
        }
    }

    public void modifyCourseFee(int courseId, BigDecimal newFee) throws SQLException {
        if (newFee == null || newFee.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Fee amount must be greater than zero");
        }
        
        String sql = "UPDATE course SET course_fee = ? WHERE course_id = ? AND isActive = TRUE";
        Connection connection = Database.getInstance().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, newFee);
            stmt.setInt(2, courseId);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Course not found");
            }
        }
    }

    public List<Map<String, Object>> getRemainingFeesForAll() throws SQLException {
        // Business Logic: Simply delegate to DAO
        return feesDAO.getAllRemainingFees();
    }

    public List<Map<String, Object>> getRemainingFeesForStudent(int studentId) throws SQLException {
        // Business Logic: Check if student has fee records
        int count = feesDAO.getStudentFeeRecordCount(studentId);
        
        if (count == 0) {
            throw new RuntimeException("No fee records found for student ID: " + studentId);
        }

        // Business Logic: Delegate to DAO for data retrieval
        return feesDAO.getStudentRemainingFees(studentId);
    }
}
