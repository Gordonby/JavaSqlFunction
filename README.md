# Java Sql Function

An Azure Function that uses Java lang to grab some data from an Azure SQL database.

## What's good about this demo

It runs locally, it runs in an Azure function.

The development experience with codespaces is fully configured.

## What's bad about this demo

It fails to connect to the SQL Server with this error, and i've run out of time to debug. Might revisit.

> com.microsoft.sqlserver.jdbc.SQLServerException: Failed to load MSAL4J Java library for performing ActiveDirectoryPassword authentication.

Tried with

```xml
        <dependency>
            <groupId>com.microsoft.sqlserver</groupId>
            <artifactId>mssql-jdbc</artifactId>
            <version>10.2.0.jre11</version>
        </dependency>
```

and 

```xml
        <dependency>
            <groupId>com.microsoft.sqlserver</groupId>
            <artifactId>mssql-jdbc</artifactId>
            <version>9.4.0.jre11</version>
        </dependency>
```