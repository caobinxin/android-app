package com.tuling.protocolbuffdemo;

import java.util.ArrayList;
import java.util.List;

import com.tuling.protocolbuffdemo.proto.TulingMessages.MyClasses;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    private byte[] mClassesInfoByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text_test = (TextView) findViewById(R.id.main_test);
        text_test.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 生成MyClasses对象classes。
        MyClasses classes = makeClassInfomation();
        // 将classes对象压缩成二进制数组
        mClassesInfoByte = classes.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // 还原二进制数据
        MyClasses classes = parserMessage();
        if (classes != null) {
            // 获取每一个学生数据
            List<MyClasses.Student> students = classes.getClassStudentsList();
            for (MyClasses.Student student : students) {
                long student_id = student.getStudentId();
                String student_name = student.getStudentName();
                int student_age = student.getStudentAge();
                MyClasses.Gender student_gender = student.getStudentGender();
                android.util.Log.e("TulingTag",
                        "Student info::ID= " + student_id + " Name= " + student_name + " Age= " + student_age
                                + " Gender= " + ((student_gender == MyClasses.Gender.FEMALE) ? "Female" : "Male"));
            }

            // 获取班级数据
            long classes_id = classes.getClassId();
            String classes_name = classes.getClassName();
            android.util.Log.e("TulingTag", "Classes info:ID = " + classes_id + " name = " + classes_name
                    + " student count = " + ((null == students) ? "0" : students.size()));

        } else {
            android.util.Log.e("TulingTag", "Button Onclick info:parser message false!");
        }
    }

    private MyClasses makeClassInfomation() {
        // 创建两个学生：Tuling1和Tuling2
        MyClasses.Student.Builder student_tuling1_build = MyClasses.Student.newBuilder();
        student_tuling1_build.setStudentId(1L);
        student_tuling1_build.setStudentName("Tuling1");
        student_tuling1_build.setStudentAge(20);
        student_tuling1_build.setStudentGender(MyClasses.Gender.MALE);

        MyClasses.Student.Builder student_tuling2_build = MyClasses.Student.newBuilder();
        student_tuling2_build.setStudentId(2L);
        student_tuling2_build.setStudentName("Tuling2");
        student_tuling2_build.setStudentAge(19);
        student_tuling2_build.setStudentGender(MyClasses.Gender.FEMALE);

        // 创建班级：设置班级ID以及班级名称
        MyClasses.Builder classes_builder = MyClasses.newBuilder();
        classes_builder.setClassId(1L);
        classes_builder.setClassName("Tuling Class");
        // 创建班级：添加班级学生列表
        ArrayList<MyClasses.Student> students = new ArrayList<MyClasses.Student>();
        students.add(student_tuling1_build.build());
        students.add(student_tuling2_build.build());
        classes_builder.addAllClassStudents(students);

        // 生成班级实例并返回
        return classes_builder.build();
    }

    private MyClasses parserMessage() {
        try {
            // 解析二进制数据，还原成为MyClasses对象
            MyClasses classes = MyClasses.parseFrom(mClassesInfoByte);
            return classes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
