# STATEMENTS IN JDBC:
In Spring JDBC, the handling of SQL statements is streamlined through various abstractions that simplify the process of executing queries and managing database connections. 
Spring provides support for the three main types of JDBC statements: Statement, PreparedStatement, and CallableStatement. Here’s an elaboration on each type, including their use cases, advantages, and common methods in the context of Spring JDBC.

## 1. Statement:
###  Use Case:
- The Statement interface in JDBC is used to execute simple SQL queries without any parameters. It is best suited for static SQL queries where no user input or variable data is involved.
### Example: 
```java
public List<Customer> getAllCustomersWithStatement() {
    return jdbcTemplate.execute((Connection connection) -> {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM customer");
        return mapCustomers(rs);
    });
}
```
### Key Points:
- Best for static queries.
- Easy to use but not efficient for repeated executions due to query parsing overhead.
### Common Methods:
- execute(String sql): Executes the given SQL statement, which may be an INSERT, UPDATE, DELETE, or DDL statement.
-	executeQuery(String sql): Executes a SQL query and returns a ResultSet.
-	executeUpdate(String sql): Executes an update statement and returns the number of affected rows.

## 2. PreparedStatement
### Use Case:
- The PreparedStatement interface is used for executing precompiled SQL statements. It allows for the inclusion of parameters, which makes it more efficient and secure compared to Statement, especially for queries that are executed multiple times or involve user input.
### Example: 
```java
public Customer getCustomerById(int id) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM customer WHERE id = ?",
        new Object[]{id},
        customerRowMapper
    );
}
public int addCustomer(Customer customer) {
    return jdbcTemplate.update(
        "INSERT INTO customer (id, name) VALUES (?, ?)",
        customer.getId(),
        customer.getName()
    );
}
```
### Advantages:
- Improved performance through precompilation of the SQL statement.
- Enhanced security against SQL injection attacks as parameters are bound separately from the SQL query.
### Common Methods:
- setInt(int parameterIndex, int x): Sets an integer parameter.
-	setString(int parameterIndex, String x): Sets a string parameter.
-	executeQuery(): Executes the prepared statement and returns a ResultSet.
-	executeUpdate(): Executes the prepared statement and returns the number of affected rows.

## 3. CallableStatement
### Use Case:
- CallableStatement is used for executing stored procedures in the database. It can handle input and output parameters, making it suitable for complex operations encapsulated within the database.
### Example: 
```java
public String getCustomerNameUsingStoredProcedure(int customerId) {
    String sql = "{call get_customer_name(?)}"; // Assume a stored procedure is defined
    return jdbcTemplate.execute(sql, (CallableStatementCallback<String>) cs -> {
        cs.setInt(1, customerId);
        cs.registerOutParameter(2, Types.VARCHAR);
        cs.execute();
        return cs.getString(2);
    });
}
```
### Advantages:
-	Allows the execution of complex operations encapsulated in stored procedures.
-	Can return multiple result sets and output parameters, making it versatile for database interactions.
### Common Methods:
-	setInt(int parameterIndex, int x): Sets an input parameter.
-	registerOutParameter(int parameterIndex, int sqlType): Registers an output parameter to be read after execution.
-	execute(): Executes the stored procedure.







