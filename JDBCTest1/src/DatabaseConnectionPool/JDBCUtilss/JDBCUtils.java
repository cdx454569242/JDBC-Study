package DatabaseConnectionPool.JDBCUtilss;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author
 * @create 2020-08-15 14:51
 */
public class JDBCUtils {

    /**
     *
     * @Description 使用C3P0的数据库连接池技术
     * @author shkstart
     * @date 下午3:01:25
     * @return
     * @throws SQLException
     */
    //数据库连接池只需提供一个即可。
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("hellc3p0");
    public static Connection getConnection1() throws SQLException{
        Connection conn = cpds.getConnection();

        return conn;
    }

    /**
     *
     * @Description 使用DBCP数据库连接池技术获取数据库连接
     * @author shkstart
     * @date 下午3:35:25
     * @return
     * @throws Exception
     */
    //创建一个DBCP数据库连接池
    private static DataSource source;
    static{
        try {
            Properties pros = new Properties();
            FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(is);
            source = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection2() throws Exception{

        Connection conn = source.getConnection();

        return conn;
    }



    /**
     * 使用Druid数据库连接池技术
     */
    private static DataSource source1;
    static{
        try {
            Properties pros = new Properties();

            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

            pros.load(is);

            source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection3() throws SQLException{
        Connection conn = source1.getConnection();
        return conn;
    }

}
