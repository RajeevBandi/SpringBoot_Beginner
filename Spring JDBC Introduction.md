# Spring JDBC Introduction

Spring JDBC is a module within the Spring Framework that provides a simplified approach to working with databases through JDBC (Java Database Connectivity). It offers a higher-level abstraction over plain JDBC, making database interactions easier and more manageable.

**JdbcTemplate:** This is the central class in Spring JDBC. It simplifies database operations by handling resource management (like opening and closing connections), error handling, and boilerplate code for executing SQL queries. It also provides methods for executing SQL queries, updates, and stored procedures.

**RowMapper:** This interface is used to map rows of a ResultSet to objects. You implement this interface to define how each row in the result set should be converted into an object.

**DataSource:** This is an interface that provides a standard way to connect to a database. In Spring JDBC, you typically configure a DataSource bean that JdbcTemplate will use to obtain database connections.
---
![This is a image]()
