package BaseDAO.DAOTest;

import BaseDAO.CustomerDAOImpl;
import Users.Customer;
import Utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author
 * @create 2020-08-14 15:45
 */
public class CustomerDAOImplTest {

    CustomerDAOImpl dao = new CustomerDAOImpl();

    @Test
    public void insert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customer = new Customer(1,"DAO添加测试1号", "DAO添加测试1号@qq.com", new Date(43534646435L));
            dao.insert(conn,customer);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,null);
        }
    }

    @Test
    public void deleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customer = new Customer(1,"DAO添加测试1号", "DAO添加测试1号@qq.com", new Date(43534646435L));
            dao.deleteById(conn,21);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,null);
        }
    }

    @Test
    public void updateById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer cust = new Customer(18,"贝多芬","beiduofen@email.com",new Date(98489761L));
            dao.update(conn,cust);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,null);
        }
    }

    @Test
    public void getCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer customerById = dao.getCustomerById(conn, 19);
            System.out.println(customerById);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,null);
        }
    }

    @Test
    public void getAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            List<Customer> all = dao.getAll(conn);
            all.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,null);
        }
    }

    @Test
    public void getCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Long count = dao.getCount(conn);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,null);
        }
    }

    @Test
    public void getMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(conn);
            System.out.println(maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,null);
        }
    }
}