package com.function;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetSqlData {
    /**
     * This function listens at endpoint "/api/GetSqlData". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/GetSqlData
     * 2. curl {your host}/api/GetSqlData?name=HTTP%20Query
     */
    @FunctionName("GetSqlData")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("name");
        String name = request.getBody().orElse(query);

        log.info("Loading application properties");
        Properties properties = new Properties();
        properties.load(DemoApplication.class.getClassLoader().getResourceAsStream("application.properties"));
        
        log.info("Connecting to the database : " + properties.getProperty("sqlservername"));
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName(properties.getProperty("sqlservername")); 
        ds.setDatabaseName("simpletableforcrud"); 
        ds.setUser(properties.getProperty("user"));
        ds.setPassword(properties.getProperty("password"));
        ds.setAuthentication("ActiveDirectoryPassword");

        try (Connection connection = ds.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SUSER_SNAME()")) {
            if (rs.next()) {
                System.out.println("You have successfully logged on as: " + rs.getString(1));
            }
        }
        
        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
    }
}
