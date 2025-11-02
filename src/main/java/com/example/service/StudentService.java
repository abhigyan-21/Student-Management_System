package com.example.service;

import com.example.dao.CourseDAO;
import com.example.dao.StudentDAO;
import com.example.model.Course;
import com.example.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;

    public StudentService(StudentDAO studentDAO, CourseDAO courseDAO) {
        this.studentDAO = studentDAO; this.courseDAO = courseDAO;
    }

    @Transactional
    public Long addStudent(Student s) {
        return studentDAO.save(s);
    }

    @Transactional
    public void updateStudent(Student s) {
        studentDAO.update(s);
    }

    @Transactional
    public void deleteStudent(Student s) {
        studentDAO.delete(s);
    }

    @Transactional(readOnly = true)
    public Student getStudent(Long id) {
        return studentDAO.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> listStudents() {
        return studentDAO.findAll();
    }

    @Transactional
    public void assignCourse(Long studentId, Long courseId) {
        Student s = studentDAO.findById(studentId);
        Course c = courseDAO.findById(courseId);
        if (s == null) throw new RuntimeException("Student not found");
        if (c == null) throw new RuntimeException("Course not found");
        s.setCourse(c);
        studentDAO.update(s);
    }
}
