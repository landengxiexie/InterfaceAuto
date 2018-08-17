package testPractice;

import java.sql.*;

public class halelel {
    public static void main(String []args) {
        Connection connection=null;
        Statement statement =null;
        try{
            String url="jdbc:postgresql://192.168.20.122:5432/h5_standard_20180626_main";
            String user="postgres";
            String password = "postgres";
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection(url, user, password);
            System.out.println("是否成功连接pg数据库"+connection);
            String sql="select t.encrypted_pwd,t.*  from t_user t where email='edit0015@edit.com'";
            statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while(resultSet.next()){
                String name=resultSet.getString(1);
                System.out.println(name);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally{
            try{
                statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }finally{
                try{
                    connection.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
