package com.example.service;

import com.example.model.Payment;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final SessionFactory sessionFactory;

    public PaymentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long save(Payment p) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(p);
    }
}
