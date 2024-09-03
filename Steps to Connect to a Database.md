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

### 3. Configure DataSource and JdbcTemplate
Spring Boot auto-configures a DataSource and JdbcTemplate bean based on the properties provided in application.properties. If you use auto-configuration, you can directly @Autowired the JdbcTemplate in your DAO classes.

If you need custom configuration for the DataSource or JdbcTemplate, you can define them explicitly in a configuration class.

```java
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/your_database_name")
                .username("your_username")
                .password("your_password")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

### 4.	Create DAO (Data Access Object) Classes

Implement DAO classes to interact with the database using JdbcTemplate. These classes should encapsulate the database operations (CRUD) and provide methods to perform actions such as inserting, updating, querying, and deleting records. Use JdbcTemplate methods to execute SQL queries and updates, and map the results to domain objects.

### 5. Create Entity Classes

Define entity classes to represent database records as Java objects. While not mandatory for Spring JDBC, entity classes facilitate mapping between database rows and application objects, making it easier to handle and manipulate data within your application.

### 6. Test Database Connectivity

Write a service or controller to test database interactions using the DAO class.



