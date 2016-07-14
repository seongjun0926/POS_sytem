package blog.naver.com.seongjun0926.pos;


import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class Table_Menu_Activity extends Activity {


    MENUSQLiteOpenHelper helper;
    SQLiteDatabase db;

    private static String TABLE_NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_menu);
        Log.i("tag", "onCreate");


        Intent i = getIntent();
        TABLE_NAME = i.getStringExtra("TABLE_NAME");
        Log.i("tag", "TABLE_NAME=" + TABLE_NAME);

        helper = new MENUSQLiteOpenHelper(Table_Menu_Activity.this, "MENU_TABLE_DATABASE.db", null, 1);
        db = openOrCreateDatabase("MENU_TABLE_DATABASE.db", MODE_WORLD_WRITEABLE, null);
        showDatabase();

    }


    private void showDatabase() {
        ListView list = (ListView) findViewById(R.id.listView3);

        boolean isOpen = openDatabase();
        if (isOpen) {
            Cursor cursor = executeRawQueryParam();
            startManagingCursor(cursor);

            String[] columns = new String[]{"name", "price", "ea"};
            int[] to = new int[]{R.id.dataItem01, R.id.dataItem02, R.id.dataItem03};

            final SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.listitem_com, cursor, columns, to);

            list.setAdapter(mAdapter);

            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //길게누르면 메뉴 삭제 리스너
                    Log.i("tag", "i=" + i);

                    Cursor c = (Cursor) mAdapter.getItem(i);

                    final String id = c.getString(i -= i);
                    Log.i("tag", "id=" + id);


                    AlertDialog.Builder alert = new AlertDialog.Builder(Table_Menu_Activity.this);

                    alert.setTitle("메뉴를 삭제하시겠습니까?");
                    alert.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    alert.setNegativeButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    delete(id);
                                }
                            });
                    alert.show();

                    return true;

                }


            });
        }

    }


    private boolean openDatabase() {
        Log.i("tag", "openDatabase");

        return true;
    }

    private Cursor executeRawQueryParam() {
        Log.i("tag", "executeRawQueryParam");

        String SQL = "select * from " + TABLE_NAME;

        Log.i("tag", "SQL=" + SQL);

        Cursor c1 = db.rawQuery(SQL, null);

        return c1;
    }

    public void delete(String id) {
        db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[]{id});
        showDatabase();
    }

}
