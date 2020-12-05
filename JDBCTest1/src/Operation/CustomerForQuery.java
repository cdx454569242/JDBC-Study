package Operation;

import Users.Customer;
import Utils.JDBCUtils;
import org.junit.Test;



import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对Customer表的查询操作
 * @author
 * @create 2020-08-13 8:09
 */
public class CustomerForQuery {

    @Test
    public void testqueryForCustomers(){
        String sql = "select id,name,birth from customers where id = ?";
        String sql1 = "select id,name,birth,email from customers where id = ?";
        Customer customer = queryForCustomers(sql1, 12);
        System.out.println(customer);
    }

    public Customer queryForCustomers(String sql, Object...args){
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

            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            if(rs.next()){
                Customer customer = new Customer();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);

                    //获取每个列的列名 通过列名给Customer对象指定的属性赋值
                    String columnName = rsmd.getColumnName(i+1);

                    //给customer对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(customer,columnValue);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,ps,rs);
        }
        return null;

    }

    @Test
    public void testQuery(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);
            //执行并返回结果集
            rs = ps.executeQuery();
            //处理结果集
            if(rs.next()){
                //判断结果集的下一条是否有数据,如果有数据返回true,并且指针下移,如果没有返回false,指针不动

                //获取当前各个字段的值
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);

                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,ps,rs);
        }
    }
}
