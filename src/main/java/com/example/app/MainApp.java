package com.example.app;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.config.AppConfig;
import com.example.dao.CourseDAO;
import com.example.model.Course;
import com.example.model.Student;
import com.example.service.FeeService;
import com.example.service.StudentService;

public class MainApp {
    public static void main(String[] args) {
        // try-with-resources ensures context and scanner are closed
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
             Scanner sc = new Scanner(System.in)) {

            StudentService studentService = ctx.getBean(StudentService.class);
            FeeService feeService = ctx.getBean(FeeService.class);

            System.out.println("Student Management Console");
            boolean running = true;

            while (running) {
                System.out.println("\nMenu:\n1. Add Student\n2. List Students\n3. View Student\n4. Update Student\n5. Delete Student\n6. Add Course\n7. List Courses\n8. Assign Course to Student\n9. Pay Fee\n10. Refund\n0. Exit");
                System.out.print("Choose: ");
                int opt;
                try {
                    opt = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid option");
                    continue;
                }

                try {
                    switch (opt) {
                        case 1:
                            System.out.print("Name: ");
                            String name = sc.nextLine();
                            System.out.print("Email: ");
                            String email = sc.nextLine();
                            Student s = new Student(name, email);
                            studentService.addStudent(s);
                            System.out.println("Added: " + s);
                            break;
                        case 2:
                            List<Student> list = studentService.listStudents();
                            list.forEach(System.out::println);
                            break;
                        case 3:
                            System.out.print("Student ID: ");
                            long id = Long.parseLong(sc.nextLine());
                            Student s3 = studentService.getStudent(id);
                            System.out.println(s3);
                            break;
                        case 4:
                            System.out.print("Student ID: ");
                            long id4 = Long.parseLong(sc.nextLine());
                            Student s4 = studentService.getStudent(id4);
                            if (s4 == null) {
                                System.out.println("Not found");
                                break;
                            }
                            System.out.print("New name (enter to skip): ");
                            String newName = sc.nextLine();
                            if (!newName.trim().isEmpty()) s4.setName(newName);
                            studentService.updateStudent(s4);
                            System.out.println("Updated: " + s4);
                            break;
                        case 5:
                            System.out.print("Student ID: ");
                            long id5 = Long.parseLong(sc.nextLine());
                            Student s5 = studentService.getStudent(id5);
                            if (s5 != null) {
                                studentService.deleteStudent(s5);
                                System.out.println("Deleted");
                            } else {
                                System.out.println("Not found");
                            }
                            break;
                        case 6:
                            System.out.print("Course name: ");
                            String cname = sc.nextLine();
                            System.out.print("Duration weeks: ");
                            int dur = Integer.parseInt(sc.nextLine());
                            Course c = new Course(cname, dur);
                            CourseDAO courseDAO = ctx.getBean("courseDAO", CourseDAO.class);
                            courseDAO.save(c);
                            System.out.println("Added course: " + c);
                            break;
                        case 7:
                            CourseDAO courseDAO7 = ctx.getBean("courseDAO", CourseDAO.class);
                            courseDAO7.findAll().forEach(System.out::println);
                            break;
                        case 8:
                            System.out.print("Student ID: ");
                            long sid = Long.parseLong(sc.nextLine());
                            System.out.print("Course ID: ");
                            long cid = Long.parseLong(sc.nextLine());
                            studentService.assignCourse(sid, cid);
                            System.out.println("Assigned course.");
                            break;
                        case 9:
                            System.out.print("Student ID: ");
                            long sid9 = Long.parseLong(sc.nextLine());
                            System.out.print("Amount: ");
                            BigDecimal amt9 = new BigDecimal(sc.nextLine());
                            feeService.payFee(sid9, amt9);
                            System.out.println("Payment recorded.");
                            break;
                        case 10:
                            System.out.print("Student ID: ");
                            long sid10 = Long.parseLong(sc.nextLine());
                            System.out.print("Amount: ");
                            BigDecimal amt10 = new BigDecimal(sc.nextLine());
                            feeService.refund(sid10, amt10);
                            System.out.println("Refund completed.");
                            break;
                        case 0:
                            running = false;
                            break;
                        default:
                            System.out.println("Unknown option");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Operation failed: " + e.getMessage());
                    e.printStackTrace(System.out);
                }
            }
        }
    }
}
