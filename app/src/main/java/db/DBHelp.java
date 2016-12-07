package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Bean.Student;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class DBHelp extends SQLiteOpenHelper {
    private final static String DB_NAME = "student.db";
    private final static int VERSION = 1;
    public DBHelp(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 插入
     */
    public void insert(String name,String age,String sex){
        SQLiteDatabase wdb = getWritableDatabase();
        wdb.execSQL("insert into student(姓名,年龄,性别) values(?,?,?)",new Object[]{name,age,sex});
        Log.e("插入","成功");
    }

    /**
     * 删除
     * @param name
     */
    public void delete(String name){
        SQLiteDatabase wdb = getWritableDatabase();
        wdb.execSQL("delete from student where 姓名 = ?",new Object[]{name});
        Log.e("删除","成功");
    }

    /**
     * 更新
     * @param name
     * @param age
     */
    public void update(String name,String age){
        SQLiteDatabase wdb = getWritableDatabase();
        wdb.execSQL("update student set 年龄 = ? where 姓名 = ?",new Object[]{age,name});
        Log.e("更新","成功");
    }
    /**
     * 查询

     * @return
     */
    public List<Student> select(){
          List<Student> mList = new ArrayList<Student>();
        SQLiteDatabase rdb = getReadableDatabase();
//        Cursor cursor = rdb.rawQuery("select 姓名,年龄,性别 from student where 姓名 = ?",new String[]{name});
        Cursor cursor = rdb.rawQuery("select 姓名,年龄,性别 from student",null);
        while (cursor.moveToNext()){
            String name1 = cursor.getString(0);
            String age = cursor.getString(1);
            String sex = cursor.getString(2);
            mList.add(new Student(name1,age,sex));
        }
        Log.e("查询","成功");
        return mList;
    }

    /**
     * 模糊查询
     * @param name
     * @return
     */
    public List<Student> mhselect(String name){
        List<Student> mList = new ArrayList<Student>();
        SQLiteDatabase rdb = getReadableDatabase();
        /**
         * desc 降序
         * ASC 升序
         */
//        drop table if exists people;//删除表people 如果 people 存在
//        create table if not exists ppp(name varchar(20));//如果表ppp不存在，创建表
//        Cursor cursor = rdb.rawQuery("select 姓名,年龄,性别 from student where 姓名 = ? order by name desc",new String[]{name});
        Cursor cursor = rdb.rawQuery("select 姓名,年龄,性别 from student where 姓名 like '%" + name +"%'",null);
        while (cursor.moveToNext()){
            String name1 = cursor.getString(0);
            String age = cursor.getString(1);
            String sex = cursor.getString(2);
            mList.add(new Student(name1,age,sex));
        }
        Log.e("查询","成功");
        return mList;
    }
}
