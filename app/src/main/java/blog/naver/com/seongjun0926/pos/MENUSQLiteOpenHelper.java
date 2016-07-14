package blog.naver.com.seongjun0926.pos;


import android.content.Context;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MENUSQLiteOpenHelper extends SQLiteOpenHelper {



    public MENUSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "create table TABLE_MENU_1 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql = "create table MENU ( _id integer primary key autoincrement, name text, price integer);";
        String sql2 = "create table TABLE_MENU_2 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql3 = "create table TABLE_MENU_3 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql4 = "create table TABLE_MENU_4 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql5 = "create table TABLE_MENU_5 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql6 = "create table TABLE_MENU_6 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql7 = "create table TABLE_MENU_7 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql8 = "create table TABLE_MENU_8 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql9 = "create table TABLE_MENU_9 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql10 = "create table TABLE_MENU_10 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql11 = "create table TABLE_MENU_11 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";
        String sql12 = "create table TABLE_MENU_12 ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";



        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);
        db.execSQL(sql9);
        db.execSQL(sql10);
        db.execSQL(sql11);
        db.execSQL(sql12);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists MENU");


        onCreate(db);
    }
}