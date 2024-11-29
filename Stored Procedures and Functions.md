## What are Stored Procedures and Functions?

### 1. **Stored Procedure**
A stored procedure is a precompiled collection of one or more SQL statements that can be executed as a single unit. It is stored in the database and can be called by applications to perform a task or query. Stored procedures can also accept input and output parameters.

### 2. **Stored Function**
A stored function is similar to a stored procedure, but it is designed to return a single value, such as an integer, string, or date. It can be used within SQL queries to perform complex calculations or operations.

---

## Why Use Stored Procedures and Functions?

- **Performance**: Stored procedures are precompiled and stored in the database, meaning that they can be executed faster than regular SQL queries. This reduces network traffic and improves application performance.
- **Security**: Stored procedures can encapsulate business logic and control access to the underlying database tables. This adds an extra layer of security.
- **Maintainability**: Business logic in stored procedures and functions can be updated directly in the database without modifying the application code.
- **Reusability**: Stored procedures and functions can be reused across multiple applications or parts of an application.

---

## Calling Stored Procedures in Spring JDBC

Spring JDBC provides multiple ways to call stored procedures, using either `JdbcTemplate`, `SimpleJdbcCall`, or `NamedParameterJdbcTemplate`.

---

### 1. **Calling Stored Procedures Using `JdbcTemplate`**

You can call a stored procedure with `JdbcTemplate` using the `CallableStatement` interface. Here's an example of calling a stored procedure:

#### Example: Calling a Stored Procedure with `JdbcTemplate`

Assuming a stored procedure `getEmployeeDetails` with an input parameter `emp_id` and output parameter `emp_name`:

```java
public class EmployeeDao {
    private JdbcTemplate jdbcTemplate;

    public EmployeeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getEmployeeName(int empId) {
        String sql = "{call getEmployeeDetails(?, ?)}"; // Calling stored procedure
        return jdbcTemplate.execute((Connection con) -> {
            CallableStatement cs = con.prepareCall(sql);
            cs.setInt(1, empId);
            cs.registerOutParameter(2, Types.VARCHAR); // Register output parameter
            cs.execute();
            return cs.getString(2); // Get the value of the output parameter
        });
    }
}

```

---

### 2. **Calling Stored Procedures Using `SimpleJdbcCall`**

`SimpleJdbcCall` is a higher-level abstraction in Spring JDBC that simplifies working with stored procedures. It allows you to call a stored procedure using a simpler and more intuitive API.

#### Example:  Calling a Stored Procedure with `SimpleJdbcCall`

```java
public class EmployeeDao {
    private SimpleJdbcCall jdbcCall;

    public EmployeeDao(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("getEmployeeDetails") // Name of the stored procedure
                .declareParameters(
                    new SqlParameter("emp_id", Types.INTEGER),
                    new SqlOutParameter("emp_name", Types.VARCHAR)
                );
    }

    public String getEmployeeName(int empId) {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("emp_id", empId);

        Map<String, Object> outParams = jdbcCall.execute(inParams);
        return (String) outParams.get("emp_name");
    }
}


```

---

### 3. **Calling Stored Procedures Using `NamedParameterJdbcTemplate`**

`NamedParameterJdbcTemplate` allows you to pass parameters by name, which can improve code readability and maintainability.

#### Example: Calling a Stored Procedure with `NamedParameterJdbcTemplate`

Assuming a stored procedure `getEmployeeDetails` with an input parameter `emp_id` and output parameter `emp_name`:

```java
public class EmployeeDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeeDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public String getEmployeeName(int empId) {
        String sql = "CALL getEmployeeDetails(:emp_id, :emp_name)";
        Map<String, Object> params = new HashMap<>();
        params.put("emp_id", empId);
        params.put("emp_name", "");  // This will be the output parameter.

        Map<String, Object> result = jdbcTemplate.call(sql, params);
        return (String) result.get("emp_name");
    }
}

```
## Calling Functions in Spring JDBC

Just like calling stored procedures, calling a function in Spring JDBC can be done using `JdbcTemplate`, `SimpleJdbcCall`, or `NamedParameterJdbcTemplate`. Functions typically return a single value that can be retrieved directly.

#### Example: Calling a Function with `SimpleJdbcCall`

Assume you have a function `getEmployeeSalary` that takes an `emp_id` and returns the salary.

```java
public class EmployeeDao {
    private SimpleJdbcCall jdbcCall;

    public EmployeeDao(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withFunctionName("getEmployeeSalary") // Name of the function
                .declareParameters(
                    new SqlParameter("emp_id", Types.INTEGER)
                );
    }

    public BigDecimal getEmployeeSalary(int empId) {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("emp_id", empId);

        return (BigDecimal) jdbcCall.executeFunction(BigDecimal.class, inParams);
    }
}
```

---

## Benefits of Using Stored Procedures and Functions in Spring JDBC

- **Encapsulation of Business Logic**: Business logic can be centralized in stored procedures or functions rather than in the application code.
- **Performance**: Since stored procedures are precompiled, they offer better performance, especially for complex queries.
- **Reusability**: The same stored procedure or function can be reused by multiple applications.
- **Security**: Control access to the database using stored procedures by granting permissions to the procedure instead of direct table access.

---

## When to Use Stored Procedures and Functions

- **Complex Operations**:  If your SQL logic involves complex queries, joins, or multi-step operations, using stored procedures or functions is ideal.
- **Performance Optimization**: For high-performance applications, stored procedures can be faster than sending multiple SQL statements from the application.
- **Security Requirements**: When you want to limit direct access to the underlying tables and expose only specific operations through stored procedures or functions.
- **Code Reusability**: When the same logic needs to be used by multiple applications or services.

---




