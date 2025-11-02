package com.example.service;

import com.example.dao.StudentDAO;
import com.example.model.Payment;
import com.example.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FeeService {
    private final StudentDAO studentDAO;
    private final PaymentService paymentService;

    public FeeService(StudentDAO studentDAO, PaymentService paymentService) {
        this.studentDAO = studentDAO;
        this.paymentService = paymentService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void payFee(Long studentId, BigDecimal amount) {
        Student s = studentDAO.findById(studentId);
        if (s == null) throw new RuntimeException("Student not found");
        // deduct balance
        s.setBalance(s.getBalance().subtract(amount));
        studentDAO.update(s);
        // record payment
        Payment p = new Payment(s, amount);
        paymentService.save(p);
        // simulated failure check (amount negative)
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new RuntimeException("Invalid amount, rollback"); 
    }

    @Transactional(rollbackFor = Exception.class)
    public void refund(Long studentId, BigDecimal amount) {
        Student s = studentDAO.findById(studentId);
        if (s == null) throw new RuntimeException("Student not found");
        s.setBalance(s.getBalance().add(amount));
        studentDAO.update(s);
        paymentService.save(new Payment(s, amount.negate()));
    }
}
