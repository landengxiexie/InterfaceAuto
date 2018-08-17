package com.test.utill;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class ConnectMysql {

    private String driver = "com.mysql.jdbc.Driver";

    private String host;

    private String user;

    private String pwd;

    private Connection conn = null;

    private Statement stmt = null;

    public void connect(String host, String user, String pwd) {
        this.close();
        this.host = host;
        this.user = user;
        this.pwd = pwd;
    }

    public synchronized Object[][] query(String sql) {
        try {
            return this.result(sql);
        } catch (SQLException e) {
            return null;
        }
    }

    public synchronized void close() {
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

    private void connectMysql() {
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

    private void statement() {
        if (conn == null) {
            this.connectMysql();
        }
        try {
            stmt = (Statement) conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private ResultSet resultSet(String sql) {
        ResultSet rs = null;
        if (stmt == null) {
            this.statement();
        }
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    private Object[][] result(String sql) throws SQLException {
        ResultSet rs = this.resultSet(sql);
        ResultSetMetaData md = rs.getMetaData();
        rs.last();
        int rowCount = rs.getRow();
        Object[][] result = new Object[rowCount][];
        Object[] temp=null;
        rs.beforeFirst();
        int rowCount1 = rs.getRow();
        int i = 0;
        while (rs.next()) {
            Map<String, String> columnMap = new HashMap<>();
            for (int j = 0; j < md.getColumnCount(); j++) {
                columnMap.put(md.getColumnName(j + 1), rs.getString(j + 1));
                temp = new Object[]{columnMap};
            }
            result[i] = temp;
            i++;
        }
/*        Object[][] objects = new Object[elements.size()][];
        for (int i = 0; i < elements.size(); i++) {
//            Object[] temp = new Object[]{this.getMergeMapData(pf.getChildrenInfoByElement(elements.get(i)),commonMap)};
            Map<String, String> mergeCommon = this.getMergeMapData(pf.getChildrenInfoByElement(elements.get(i)), commonMap);
            Map<String, String> mergeGlobal = this.getMergeMapData(mergeCommon, Global.global);
            Object[] temp = new Object[]{mergeGlobal};
            objects[i] = temp;
        }*/
        return result;
    }

/*
    private  Object[][] result(String sql) {
        ResultSet rs = this.resultSet(sql);
        List<Object[]> result = new ArrayList<>();
        try {
            int colNum = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] str = new String[colNum];
                for (int i = 0; i < str.length; i++) {
                    str[i] = rs.getString(i + 1);
                }
                result.add(str);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] results = new Object[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            results[i] = result.get(i);
        }
        return results;
    }
*/


    public static void main(String[] args) throws SQLException {
     /*   ConnectMysql c=new ConnectMysql();
        c.connect(BaseConfig.mysqlhost,BaseConfig.mysqlusername,BaseConfig.mysqlpassword);
        Object[][] query = c.query(BaseConfig.mysqlsql);
        for(int i=0;i<query.length;i++){
            for(int j=0;j<query[i].length;j++){
                System.out.println(query[i][j]);
            }
            System.out.println();
        }*/

    }
}