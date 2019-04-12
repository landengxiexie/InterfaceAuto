package utill;


import com.bean.BaseConfig;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("ALL")
public class ConnectPostgreSQL {
    private static String driver = "org.postgresql.Driver";
    private Connection conn = null;
    private Statement stmt = null;
    private String host;
    private String user;
    private String pwd;

    public void connect(String host, String user, String pwd) {
        this.close();
        this.host = host;
        this.user = user;
        this.pwd = pwd;
    }

    public String queryValue(String sql) throws SQLException {
        ResultSet rs = this.resultSet(sql);
        String uid=null;
        while (rs.next()) {
             uid = rs.getString(1);
        }
        rs.close();
        conn.close();
        return uid;
    }

    private Object[][] result(String sql) throws SQLException {
        ResultSet rs = this.resultSet(sql);
        ResultSetMetaData md = rs.getMetaData();
        rs.last();
        int rowCount = rs.getRow();
        Object[][] result = new Object[rowCount][];
        Object[] temp = null;
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
        return result;
    }

    public synchronized Object[][] query(String sql) {
        try {
            return this.result(sql);
        } catch (SQLException e) {
            return null;
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

    private void statement() {
        if (conn == null) {
            this.connectPostgreSQL();
        }
        try {
             stmt =  conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectPostgreSQL() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(BaseConfig.postgresqlhost, BaseConfig.postgresqlusername, BaseConfig.postgresqlpassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private synchronized void close() {
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
    public static void main(String[] args) throws SQLException {
       /*ConnectPostgreSQL c=new ConnectPostgreSQL();
        c.connect(BaseConfig.postgresqlhost,BaseConfig.postgresqlusername,BaseConfig.postgresqlpassword);
         Object[][] query = c.query(BaseConfig.postgresqlsql);
        for(int i=0;i<query.length;i++){
            for(int j=0;j<query[i].length;j++){
                System.out.println(query[i][j]);
            }
            System.out.println();
        }*/
    }

}