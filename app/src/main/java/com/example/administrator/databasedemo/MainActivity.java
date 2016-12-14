package com.example.administrator.databasedemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import Bean.Student;
import db.DBHelp;

public class MainActivity extends AppCompatActivity {


    public String path;
    public String db_name;
    public String databaseFilename;
    private DBHelp dbHelp = null;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        path = "/data/data/com.example.administrator.databasedemo/databases/";//第一种方式简单
        //下面是第二种方式
//        String pathfile = getFilesDir().getAbsolutePath();
//        path = getPath(pathfile);
//        Log.e("路径:",path);
        db_name = "student.db";
        databaseFilename = path + db_name;
        copyDataBase();
        dbHelp = new DBHelp(this);



    }

    public void add(View view) {
        dbHelp.insert("王五", "18", "nv");
    }

    public void delete(View view) {
        dbHelp.delete("王五");
    }
    public void update(View view) {
        dbHelp.update("王五","222");
    }
    public void select(View view) {
        students = dbHelp.select();
        for (Student student: students){
            Log.e("student",student.toString());
        }
    }
    public void mhselect(View view) {
        List<Student> students = dbHelp.mhselect("李");
        for (Student student:students){
            Log.e("student",student.toString());
        }
    }

    /**
     * 通过 String pathfile = getFilesDir().getAbsolutePath()获取数据库路径，不包括xxx.db;
     *
     * @param path
     */
    private String getPath(String path) {
        String result = null;
//        String path ="/data/data/com.example.administrator.databasedemo/databases";
        String[] split = path.split("/");
        String[] arra = new String[split.length - 1];
        for (int i = 0; i < split.length - 1; i++) {
            arra[i] = split[i];
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arra.length; i++) {
            sb.append(arra[i] + "/");

        }

        result = new String(sb.append("databases/"));
        return result;
    }

    /**
     * 复制数据库
     */
    private void copyDataBase() {
        if (!isExits()) {
            FileOutputStream fo = null;
            InputStream inputStream = null;
            try {
                inputStream = getAssets().open(db_name);
                byte[] buffer = new byte[1024];
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                fo = new FileOutputStream(databaseFilename);
                int length = 0;
                while ((length = inputStream.read(buffer)) != -1) {
                    fo.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("没找到", e.getMessage());
            } finally {
                if (fo != null) {
                    try {
                        fo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    /**
     * 判断该手机中是否存在数据库
     *
     * @return
     */
    public boolean isExits() {

        SQLiteDatabase checkDB = null;
        File file = new File(databaseFilename);
        if (file.exists()) {
            checkDB = SQLiteDatabase.openDatabase(databaseFilename, null,
                    SQLiteDatabase.OPEN_READONLY);
            if (checkDB != null) {
                checkDB.close();
            }
        } else {
            checkDB = null;
        }
        return checkDB != null ? true : false;
    }

}
