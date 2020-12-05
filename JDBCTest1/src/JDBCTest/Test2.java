package JDBCTest;

import Operation.PreparedStatementQueryTest;
import Operation.PreparedStatementUpdateTest;
import org.junit.Test;

import java.awt.geom.Area;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * @author
 * @create 2020-08-13 15:13
 */
public class Test2 {
    // 问题1：向examstudent表中添加一条记录
	/*
	 *  Type:
		IDCard:
		ExamCard:
		StudentName:
		Location:
		Grade:
	 */
    @Test
    public void test1(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("四级/六级：");
        int type = scanner.nextInt();
        System.out.print("身份证号：");
        String IDCard = scanner.next();
        System.out.print("准考证号：");
        String examCard = scanner.next();
        System.out.print("学生姓名：");
        String studentName = scanner.next();
        System.out.print("所在城市：");
        String location = scanner.next();
        System.out.print("考试成绩：");
        int grade = scanner.nextInt();

        String sql = "insert into examstudent(type,IDCard,examCard,studentName,location,grade)values(?,?,?,?,?,?)";
        PreparedStatementUpdateTest update = new PreparedStatementUpdateTest();
        int result = update.update(sql, type, IDCard, examCard, studentName, location, grade);
        if(result>0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }
    }

    @Test
    public void test2(){
        //问题2：根据身份证号或者准考证号查询学生成绩信息

        System.out.println("请选择您要输入的类型：");
        System.out.println("a.准考证号");
        System.out.println("b.身份证号");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();

        PreparedStatementQueryTest query = new PreparedStatementQueryTest();

        if("a".equalsIgnoreCase(selection)){
            System.out.println("输入准考证号");
            String examCard = scanner.next();
            String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade " +
                    "from examstudent where examCard = ?";
            Student student = query.getInstance(Student.class, sql, examCard);
            if (student!=null){
                System.out.println(student);
            }else{
                System.out.println("查询失败");
            }
        }else if("b".equalsIgnoreCase(selection)){
            System.out.println("输入身份证号");
            String IDCard = scanner.next();
            String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade " +
                    "from examstudent where IDCard = ?";
            Student student = query.getInstance(Student.class, sql, IDCard);
            if (student!=null){
                System.out.println(student);
            }else{
                System.out.println("查询失败");
            }
        }else{
            System.out.println("输入有误");
        }
    }

    //删除指定学生信息
    @Test
    public void test3(){
        System.out.println("输入学生的考号:");
        Scanner sc = new Scanner(System.in);
        String examCard = sc.next();

        String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade " +
                "from examstudent where examCard = ?";
        PreparedStatementQueryTest query = new PreparedStatementQueryTest();
        Student student = query.getInstance(Student.class, sql, examCard);
        if(student == null){
            System.out.println("查无此人");
        }else{
            String deleteSql = "delete from examstudent where examCard = ?";
            PreparedStatementUpdateTest update = new PreparedStatementUpdateTest();
            int deleteResult = update.update(deleteSql, examCard);
            if(deleteResult>0){
                System.out.println("删除成功");
                System.out.println(student);
            }else{
                System.out.println("删除失败");
            }
        }
    }

    //优化删除学生信息方法
    @Test
    public void test4(){
        System.out.println("输入学生的考号:");
        Scanner sc = new Scanner(System.in);
        String examCard = sc.next();
        String deleteSql = "delete from examstudent where examCard = ?";
        PreparedStatementUpdateTest update = new PreparedStatementUpdateTest();
        int deleteResult = update.update(deleteSql, examCard);
        if(deleteResult>0){
            System.out.println("删除成功");
        }else{
            System.out.println("查无此人");
        }
    }
}
