package JDBCTest;

import Operation.PreparedStatementUpdateTest;

import java.util.Scanner;

/**
 * @author
 * @create 2020-08-13 14:58
 */
public class TestInsert {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入用户名:");
        String name = sc.next();

        System.out.println("输入邮箱:");
        String email = sc.next();

        System.out.println("输入生日");
        String birth = sc.next();

        PreparedStatementUpdateTest update = new PreparedStatementUpdateTest();
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        int result = update.update(sql, name, email, birth);
        if(result>0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }
    }
}
