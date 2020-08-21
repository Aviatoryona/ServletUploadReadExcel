package dev.yonathaniel.servetuploadreadexcel.db;

import java.sql.*;

public class DbConnection implements DbConnectionI {

    // TODO: 8/7/2020 set static connection attributes
    private String username = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/systechtraing";

    private final Connection connection;

    private DbConnection() throws SQLException, ClassNotFoundException {
        this.connection = getConnection();
    }

    private DbConnection(String username, String password, String url) throws SQLException, ClassNotFoundException {
        this.username = username;
        this.password = password;
        this.url = url;
        this.connection = getConnection();
    }

    public static DbConnection getInstance(String url, String username, String password) throws SQLException, ClassNotFoundException {
        return new DbConnection(url, username, password);
    }

    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        return new DbConnection();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection != null) {
            return connection;
        }
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(sql);
    }

    @Override
    public boolean execute(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate() != -1;
    }

    @Override
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.connection.prepareStatement(sql);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
        return statement.execute(sql);
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }

}
