## Connection Pooling in Spring JDBC

### What is Connection Pooling?

Connection pooling is a technique used to maintain a cache (pool) of database connections that can be reused across multiple requests, reducing the overhead of repeatedly creating and closing connections. This improves the performance of database-intensive applications by reusing existing connections instead of opening new ones every time a connection is required.

### Why Connection Pooling is Important?

- **Resource Management:** Opening and closing database connections is resource-intensive. Connection pooling minimizes the cost by reusing existing connections.
- **Performance Improvement:** By reusing connections, connection pooling reduces the latency associated with establishing new database connections.
- **Concurrency Handling:** Connection pools can manage the distribution of connections across multiple threads, ensuring that connections are efficiently used and not wasted.
- **Configuration Flexibility:** Connection pools allow fine-tuning of parameters such as maximum pool size, connection timeout, idle timeout, etc., enabling optimization based on application needs.

### How Connection Pooling Works in Spring JDBC

In Spring JDBC, connection pooling can be integrated via a connection pool manager such as HikariCP, Apache DBCP, or Tomcat JDBC. Spring Boot simplifies the integration of connection pooling by providing default configurations and auto-configuration support.

### Steps to Implement Connection Pooling in Spring JDBC

1. **Choose a Connection Pool Implementation:** Spring Boot supports various connection pool libraries like HikariCP (default), Apache DBCP, Tomcat JDBC, and more. HikariCP is the recommended default due to its performance and simplicity.
2. **Configure the DataSource:** In Spring JDBC, you define the `DataSource` bean, which manages the connection pool. Spring Boot simplifies this by automatically configuring a `DataSource` based on the selected connection pool implementation.
3. **Customize Pool Properties:** You can customize the connection pool properties in the `application.properties` or `application.yml` file. These properties include maximum pool size, idle timeout, connection timeout, and more.

### Key Properties for Connection Pooling

- **`spring.datasource.hikari.maximum-pool-size:`** The maximum number of connections that can be in the pool at the same time.
- **`spring.datasource.hikari.minimum-idle:`** The minimum number of idle connections that the pool maintains, even if no connections are in use.
- **`spring.datasource.hikari.idle-timeout:`** The maximum amount of time that a connection can sit idle in the pool before being removed.
- **`spring.datasource.hikari.max-lifetime:`** The maximum lifetime of a connection in the pool before it is closed and removed.
- **`spring.datasource.hikari.connection-timeout:`** The maximum number of milliseconds that the pool will wait for a connection to be available before throwing an exception.
- **`spring.datasource.hikari.pool-name:`** The name of the connection pool.
