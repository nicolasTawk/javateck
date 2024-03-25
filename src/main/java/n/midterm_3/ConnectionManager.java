package n.midterm_3;

import java.sql.*;

public class ConnectionManager {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Pharmacy_db";
    private static final String USER = "root";
    private static final String PASS = "nicolas5t6u9";

    // Connection object
    private Connection connection;

    // Constructor to establish the database connection
    public ConnectionManager() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            // Error handling for connection failure
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            // Error handling for connection closure failure
            System.err.println("Error closing the connection: " + e.getMessage());
        }
    }

    // Method to execute a query and return a result set
    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            // Error handling for query execution failure
            System.err.println("Error executing query: " + e.getMessage());
        }
        return resultSet;
    }

    // Method to execute a parameterized query and return a result set
    public ResultSet executeQuery(String query, Object... params) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            // Error handling for query execution failure
            System.err.println("Error executing query: " + e.getMessage());
        }
        return resultSet;
    }

    // Method to execute an update (insert, update, delete) operation
    public int executeUpdate(String query) {
        int rowsAffected = 0;
        try {
            Statement statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException e) {
            // Error handling for update execution failure
            System.err.println("Error executing update: " + e.getMessage());
        }
        return rowsAffected;
    }

    // Method to execute a parameterized update (insert, update, delete) operation
    public int executeUpdate(String query, Object... params) {
        int rowsAffected = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            // Error handling for update execution failure
            System.err.println("Error executing update: " + e.getMessage());
        }
        return rowsAffected;
    }

    // Method to start a transaction
    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    // Method to commit a transaction
    public void commitTransaction() throws SQLException {
        connection.commit();
    }

    // Method to get the underlying connection object
    public Connection getConnection() {
        return connection;
    }

    // Method to rollback a transaction
    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
