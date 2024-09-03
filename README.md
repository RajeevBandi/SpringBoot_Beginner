# Steps to Connect to a Database in Spring Boot

### 1. Add the Database Dependency

The first step is to include the appropriate database dependency in your `pom.xml` file such as PostgreSQL or MySQL. Spring Boot will automatically configure the connection based on the included dependencies.

```xml
<!-- Example for PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.3.3</version>
</dependency>

<!-- Example for MySQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
</dependency>
```

### 2. Configure Database Properties

In your application.properties (or application.yml), you need to specify the necessary properties to connect to the database. Here’s an example for a PostgreSQL database:

```properties
#For Postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/your_postgres_database
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
spring.datasource.driver-class-name=org.postgresql.Driver

# For MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/your_mysql_database?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```


