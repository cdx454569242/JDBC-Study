package Utils;

import org.apache.commons.dbutils.DbUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

/**
 * @author
 * @create 2020-08-13 7:43
 */
public class JDBCUtils {

    public static Connection getConnection() throws Exception {
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
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static void colseResource(Connection conn, PreparedStatement ps){
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

    public static void colseResource(Connection conn, PreparedStatement ps, ResultSet rs){
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
        try {
            if(rs!=null)
                rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //使用dbutils.jar提供的DbUtils工具类实现资源关闭
    public static void colseResource1(Connection conn, PreparedStatement ps, ResultSet rs){
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }
}
