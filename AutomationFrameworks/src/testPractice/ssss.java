package com.test.utill;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
public class ssss {

    public static String driver = "com.mysql.jdbc.Driver";

    private static String host;

    private static String user;

    private static String pwd;

    private static Connection conn = null;

    private static Statement stmt = null;

    public static void connect(String host, String user, String pwd) {
        ssss.close();
        ssss.host = host;
        ssss.user = user;
        ssss.pwd = pwd;
    }

    public static synchronized List<HashMap<String, String>> query(String sql) {
        return ssss.result(sql);
    }

    public static synchronized void close() {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void connectMySQL() {
        try {
            Class.forName(driver).newInstance();
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://"
                            + host + "?useUnicode=true&characterEncoding=UTF8", user,
                    pwd);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void statement() {
        if (conn == null) {
            ssss.connectMySQL();
        }
        try {
            stmt = (Statement) conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultSet resultSet(String sql) {
        ResultSet rs = null;
        if (stmt == null) {
            ssss.statement();
        }
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private static List<HashMap<String, String>> result(String sql) {
        ResultSet rs = ssss.resultSet(sql);
        List<HashMap<String, String>> result = new ArrayList<>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int cc = md.getColumnCount();
            while (rs.next()) {
                HashMap<String, String> columnMap = new HashMap<>();
                for (int i = 0; i < cc; i++) {
                    columnMap.put(md.getColumnName(i+1), rs.getString(i+1));
                }
                result.add(columnMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {
        ssss.connect("127.0.0.1/webdriver", "root", "");
        List<HashMap<String, String>> rs = ssss.query("SELECT * from user");
        System.out.println(rs.get(1).get("username"));
        System.out.println(rs.get(1).get("password"));
        ssss.close();
    }
}