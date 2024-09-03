## JDBC Template

`JdbcTemplate` is a central class in Spring's JDBC core package, designed to simplify and streamline the use of JDBC (Java Database Connectivity). It provides a high-level abstraction for interacting with databases, handling common tasks and errors associated with JDBC, and improving code readability and maintainability.

### Core Responsibilities

- **Simplification:** `JdbcTemplate` abstracts away the repetitive boilerplate code involved in setting up and executing JDBC operations. This includes handling database connections, statements, and result sets.
- **Error Handling:** It manages JDBC exceptions by translating them into more meaningful and consistent exceptions from the `org.springframework.dao` package. This improves error handling and reduces the complexity of managing SQL errors.
- **Workflow Management:** `JdbcTemplate` handles core JDBC workflows, allowing developers to focus on writing SQL queries and extracting results rather than managing the intricacies of JDBC.

### Common Methods of `JdbcTemplate`

1. **`public int update(String query)`**
   - **Description:** Executes a SQL statement that modifies the database, such as an `INSERT`, `UPDATE`, or `DELETE` operation. This method does not return any results from the query; instead, it returns the number of rows affected.
   - **Use Case:** Useful for operations where you need to update the database but do not require a result set.

2. **`public int update(String query, Object… args)`**
   - **Description:** Similar to the previous method but allows the use of `PreparedStatement` with parameters. This is useful for parameterized queries where you need to pass dynamic values.
   - **Use Case:** Ideal for scenarios where you want to execute an SQL statement with variable parameters, ensuring security against SQL injection.

3. **`public T execute(String sql, PreparedStatementCallback action)`**
   - **Description:** Executes a SQL statement using a `PreparedStatementCallback` for custom database operations. This method is useful when you need to perform complex operations that go beyond simple queries or updates.
   - **Use Case:** Useful for operations where you need full control over the `PreparedStatement`, such as executing stored procedures or batch operations.

4. **`public void execute(String query)`**
   - **Description:** Executes a SQL statement that does not return a result set, such as Data Definition Language (DDL) statements (`CREATE`, `ALTER`, `DROP`). This method is typically used for modifying the database schema.
   - **Use Case:** Suitable for executing statements that define or alter the structure of database tables or other schema objects.

5. **`public T query(String sql, ResultSetExtractor result)`**
   - **Description:** Executes a query and processes the `ResultSet` using a `ResultSetExtractor`. This method is used to fetch complex results from the database, such as aggregating data or mapping rows to complex objects.
   - **Use Case:** Ideal for scenarios where you need to handle and transform the results of a query in a custom way, such as when processing a large result set or aggregating data into a single result.
