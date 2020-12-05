package Operation;

import Users.Customer;
import Users.Order;
import Utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2020-08-13 9:27
 */
public class PreparedStatementQueryTest {
    @Test
    public void testGetForList(){
        String sql = "select id,name,email from customers where id > ?";
        List<Customer> forList = getForList(Customer.class, sql, 5);
        for (Customer customer : forList) {
            System.out.println(customer);
        }
    }

    public <T> List<T> getForList(Class<T> clazz,String sql,Object...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            // 获取结果集的元数据 :ResultSetMetaData
            rs = ps.executeQuery();

            // 通过ResultSetMetaData获取结果集中的列数
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            //创建集合对象
            ArrayList<T> list = new ArrayList<>();
            while(rs.next()){
                T t = clazz.newInstance();

                // 处理结果集一行数据中的每一个列:给t对象指定的属性赋值
                for (int i = 0; i <columnCount; i++) {
                    // 获取列值
                    Object value = rs.getObject(i + 1);
                    // 获取每个列的列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,value);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,ps,rs);
        }
        return null;
    }

    @Test
    public void test(){
        String sql = "select id,name from customers where id = ?";
        Customer customer = getInstance(Customer.class, sql, 2);
        System.out.println(customer);

        String sql1 = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
        Order order = getInstance(Order.class, sql1, 1);
        System.out.println(order);
    }

    /**
     * @Description 针对于不同的表的通用的查询操作，返回表中的一条记录
     */
    public <T> T getInstance(Class<T> clazz, String sql, Object...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();

            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();

            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                T t = clazz.newInstance();

                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {

                    // 获取列值
                    Object columnValue = rs.getObject(i + 1);

                    // 获取每个列的列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,ps,rs);
        }
        return null;

    }
}
