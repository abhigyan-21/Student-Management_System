package com.example.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.dao.StudentDAO;
import com.example.dao.CourseDAO;
import com.example.service.StudentService;
import com.example.service.FeeService;
import com.example.service.PaymentService;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:studentdb;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        ds.setUsername("sa"); ds.setPassword(""); return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(ds);
        lsfb.setPackagesToScan("com.example.model");
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");
        lsfb.setHibernateProperties(props);
        return lsfb;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sf) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sf); return tx;
    }

    @Bean
    public StudentDAO studentDAO(SessionFactory sf) { return new StudentDAO(sf); }

    @Bean
    public CourseDAO courseDAO(SessionFactory sf) { return new CourseDAO(sf); }

    @Bean
    public StudentService studentService(StudentDAO sd, CourseDAO cd) { return new StudentService(sd, cd); }

    @Bean
    public PaymentService paymentService(SessionFactory sf) { return new PaymentService(sf); }

    @Bean
    public FeeService feeService(StudentDAO sd, PaymentService ps) { return new FeeService(sd, ps); }
}
