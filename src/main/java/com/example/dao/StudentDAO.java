package com.example.dao;

import com.example.model.Student;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAO {
    private final SessionFactory sessionFactory;

    public StudentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long save(Student s) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(s);
    }

    public void update(Student s) {
        Session session = sessionFactory.getCurrentSession();
        session.update(s);
    }

    public void delete(Student s) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(s);
    }

    public Student findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Student.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Student> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Student").list();
    }
}
