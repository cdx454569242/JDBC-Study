package Operation;

import Utils.JDBCUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @author
 * @create 2020-08-12 16:12
 */
public class PreparedStatementUpdateTest {

    @Test
    public void tesstCommonUpdate(){
//        String sql = "delete from customers where id = ?";
//        update(sql,19);

        String sql = "update `order` set order_name = ? where order_id = ?";
        update(sql,"DD",2);
    }
    //通用的增删改操作
    public int update(String sql,Object...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            /*
             * ps.execute():
             * 如果执行的是查询操作，有返回结果，则此方法返回true;
             * 如果执行的是增、删、改操作，没有返回结果，则此方法返回false.
             */
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,ps);
        }
        return 0;
    }

    @Test
    //修改记录
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句,返回PreparedStatement的实例
            String sql = "update customers set name = ? where id = ?";
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            ps.setObject(1,"莫扎特");
            ps.setObject(2,18);
            //4.执行
            ps.execute();
            //5.资源的关闭
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,ps);
        }
    }

    //增加记录
    @Test
    public void testInsert(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.读取配置文件中的4个基本信息
            BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\OpenSources\\JDBCTest\\JDBCTest1\\jdbc.properties"));
            Properties properties = new Properties();
            properties.load(bufferedReader);

            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            //2.加载驱动
            Class.forName(driverClass);

            //3.获取连接
            conn = DriverManager.getConnection(url, user, password);

            //4.预编译SQL语句,返回PreparedStatement实例
            String sql = "insert into customers (name,email,birth) values(?,?,?)";//?:占位符
            ps = conn.prepareStatement(sql);
            //5.填充占位符
            ps.setString(1,"abc");
            ps.setString(2,"abc@email.com");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1999-4-1");
            ps.setDate(3, new java.sql.Date( date.getTime() ));
            //6.执行sql语句
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            //7.资源的关闭
            try {
                if(ps!=null)
                    ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if(conn!=null)
                    conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
