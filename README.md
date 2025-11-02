Student Management System - Console App (Spring + Hibernate + H2)
----------------------------------------------------------------
Structure included for VS Code. Maven project.

How to run (step-by-step)
1. Install JDK 11+ and Maven.
2. Open project in VS Code.
3. Build:
   mvn clean package
4. Run jar-with-dependencies:
   java -jar target/student-management-1.0-SNAPSHOT-jar-with-dependencies.jar
5. Use console menu to operate.

Notes
- Uses H2 in-memory DB. Switch to MySQL by changing DataSource in AppConfig and adding MySQL driver in pom.xml.
- Transaction management demonstrated in FeeService.payFee and refund.
- Entities: Student, Course, Payment
- DAO uses Hibernate SessionFactory for session management.
