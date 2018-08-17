package testPractice;

import java.sql.*;
import java.util.Scanner;

public class aaaaaaaaaaa {
    static String url = "jdbc:postgresql://192.168.20.122:5432/h5_standard_20180626_main";
    static String usr = "postgres";
    static String psd = "postgres";
    static String sql,id,name;
    static boolean flag=false;
    static Connection conn=null;
    static Statement st=null;
    static ResultSet rs;
    static Scanner reader;
    static void conTable(){
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, usr, psd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        if(conn!=null)
        {
            System.out.println("connection success!");
        }else{
            System.out.println("connection fail!");
        }
    }

    static void show(){
        try {
            st = conn.createStatement();
            sql="select t.encrypted_pwd,t.*  from t_user t where email='edit0015@edit.com'";
            rs = st.executeQuery(sql);
            System.out.println("show the table:");
            while (rs.next()) {
                System.out.print(rs.getString("encrypted_pwd"));
                System.out.print("  ");
                System.out.println(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*
    static void insert(){
        reader=new Scanner(System.in);
        System.out.println("input insert name:");
        id=reader.next();
        name=reader.next();
        sql="insert into std values('"+id+"','"+name+"')";
        try {
            flag=st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(flag)
            System.out.println("insert success!");
        else
            System.out.println("insert fail!");
        flag=false;
    }
*/

/*    static void update(){
        reader=new Scanner(System.in);
        System.out.println("input id(you want to update):");
        id=reader.next();
        System.out.println("input name(you want to update):");
        name=reader.next();
        sql="update std set name='"+name+"' where id='"+id+"'";
        try {
            flag=st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(flag)
            System.out.println("insert success!");
        else
            System.out.println("insert fail!");
        flag=false;
    }

    static void delete(){
        System.out.println("input delete name:");
        reader=new Scanner(System.in);
        name=reader.next();
        sql="delete from std where name='"+name+"'";
        try {
            flag=st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(flag)
            System.out.println("delete success!");
        else
            System.out.println("delete false!");
        flag=false;
    }*/
    public static void main(String[] args) {
        conTable();
        show();
    }
}
