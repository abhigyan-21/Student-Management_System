package com.example.dao;

import com.example.model.Course;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDAO {
    private final SessionFactory sessionFactory;
    public CourseDAO(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public Long save(Course c) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(c);
    }

    public Course findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Course.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Course> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Course").list();
    }
}
