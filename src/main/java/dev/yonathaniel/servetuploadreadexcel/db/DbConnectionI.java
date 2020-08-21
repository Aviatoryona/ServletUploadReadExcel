package dev.yonathaniel.servetuploadreadexcel.db;

import java.sql.*;

public interface DbConnectionI {

//    Connection getConnection() throws ClassNotFoundException, SQLException;
    ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException;

    ResultSet executeQuery(String sql) throws SQLException;

    boolean execute(PreparedStatement preparedStatement) throws SQLException;

//    boolean execute(String sql, Object[][] params) throws SQLException;
    PreparedStatement getPreparedStatement(String sql) throws SQLException;

    boolean execute(String sql) throws SQLException;

    void close() throws SQLException;
}
