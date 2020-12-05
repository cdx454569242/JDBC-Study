package BlobTest;

import Users.Customer;
import Utils.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author
 * @create 2020-08-14 8:04
 */
public class BlobTest {

    //向表插入blob类型数据
    @Test
    public void test1(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();

            String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";

            ps = conn.prepareStatement(sql);

            ps.setObject(1,"abc");
            ps.setObject(2,"abc@qq.com");
            ps.setObject(3,"1999-9-9");
            FileInputStream is = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\83473237_p0.png"));
            ps.setBlob(4,is);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn,ps);
        }
    }

    //查询blob类型的字段
    @Test
    public void test2(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,20);
            rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

                //将Blob类型字段下载,以文件方式保存在本地
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\123.png");
                byte[] buffer = new byte[1024];
                int len;
                while((len=is.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{

            try {
                if(is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.colseResource(conn,ps,rs);
        }
    }
}
