package blog.naver.com.seongjun0926.pos;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)


public class Table_Select_Activity extends Activity {

    String db_name = null;
    int db_price = 0;
    int db_compute = 0;
    SQLiteDatabase db;
    private static String TABLE_NAME;
    MENUSQLiteOpenHelper helper;
    Intent intent;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_select);


        Log.i("tag", "onCreate");
        Intent i = getIntent();
        TABLE_NAME=i.getStringExtra("TABLE_NAME");
        Log.i("tag","TABLE_NAME="+TABLE_NAME);

        helper = new MENUSQLiteOpenHelper(Table_Select_Activity.this, "MENU_TABLE_DATABASE.db", null, 1);
        db = openOrCreateDatabase("MENU_TABLE_DATABASE.db", MODE_WORLD_WRITEABLE, null);

        showDatabase();
        intent = new Intent(this, TableActivity.class);

    }

    public void select(String id) {
        Log.i("tag", "select");
        db = helper.getReadableDatabase();
        Log.i("tag", "db=" + db);

        Cursor c = db.rawQuery("select * from MENU where _id = " + id, null);

        while (c.moveToNext()) {
            db_name = c.getString(c.getColumnIndex("name"));
            db_price = c.getInt(c.getColumnIndex("price"));
            Log.i("tag", "db_name=" + db_name);
            Log.i("tag", "db_price=" + db_price);


        }

    }


    private boolean openDatabase() {
        Log.i("tag", "openDatabase");

        return true;
    }


    private void showDatabase() {
        Log.i("tag", "showDatabase");

        list = (ListView) findViewById(R.id.listView2);

        boolean isOpen = openDatabase();
        if (isOpen) {
            Cursor cursor = executeRawQueryParam();
            startManagingCursor(cursor);

            Log.i("tag", "cursor=" + cursor);

            String[] columns = new String[]{"name", "price"};
            int[] to = new int[]{R.id.name_entry, R.id.price_entry};


            final SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.listitem, cursor, columns, to);
            list.setAdapter(mAdapter);
            db.close();

            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("tag", "i=" + i);

                    Cursor c = (Cursor) mAdapter.getItem(i);

                    String name = c.getString(i -= i);
                    Log.i("tag", "name=" + name);

                    select(name);

                    AlertDialog.Builder alert = new AlertDialog.Builder(Table_Select_Activity.this);


                    alert.setTitle("수량");
                    final EditText input = new EditText(Table_Select_Activity.this);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setView(input);

                    alert.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
                    alert.setNegativeButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Editable value = input.getText();


                                    if (value.length() != 0) {
                                        String value1 = value.toString();
                                        int db_value = Integer.parseInt(value1);
                                        Log.i("tag", "db_value=" + db_value);
                                        db_compute = (db_price * db_value);
                                        Log.i("tag", "db_compute=" + db_compute);
                                        insert(db_name, db_price, db_value, db_compute);


                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    Toast.makeText(Table_Select_Activity.this, "올바른 입력을 해주세요.", Toast.LENGTH_SHORT).show();

                                }
                            });
                    alert.show();

                    return true;

                }


            });
        }

    }

    public void insert(String name, int price, int ea, int compute) {
        //디비에 연결해서 인서트 하기
        Log.i("tag", "insert");
        Log.i("tag", "name=" + name);
        Log.i("tag", "price=" + price);
        Log.i("tag", "number=" + ea);
        Log.i("tag", "compute=" + compute);

        db = helper.getWritableDatabase();
        Log.i("tag", "db=" + db);

        ContentValues values = new ContentValues();
        Log.i("tag", "values");

        values.put("name", name);
        values.put("price", price);
        values.put("ea", ea);
        values.put("compute", compute);

        db.insert(TABLE_NAME, null, values);

        Cursor cursor = executeRawQueryParam1();


    }






    private Cursor executeRawQueryParam() {
        //커서를 사용해서 디비에 뭐가 저장되어있는지 알 수있는 함수.
        Log.i("tag", "executeRawQueryParam1");

        String SQL = "select * from MENU";

        Log.i("tag", "SQL=" + SQL);

        Cursor c1 = db.rawQuery(SQL, null);

        while (c1.moveToNext()) {
            String db_name = c1.getString(c1.getColumnIndex("name"));
            int db_price = c1.getInt(c1.getColumnIndex("price"));

            Log.i("tag", "db_name=" + db_name);
            Log.i("tag", "db_price=" + db_price);

        }
        return c1;
    }

    private Cursor executeRawQueryParam1() {
        Log.i("tag", "executeRawQueryParam");

        String SQL = "select * from "+TABLE_NAME;

        Log.i("tag", "SQL=" + SQL);

        Cursor c1 = db.rawQuery(SQL, null);

        while (c1.moveToNext()) {
            String db_name = c1.getString(c1.getColumnIndex("name"));
            int db_price = c1.getInt(c1.getColumnIndex("price"));
            int db_ea = c1.getInt(c1.getColumnIndex("ea"));
            int db_compute = c1.getInt(c1.getColumnIndex("compute"));

            Log.i("tag", "db_name=" + db_name);
            Log.i("tag", "db_price=" + db_price);
            Log.i("tag", "db_ea=" + db_ea);
            Log.i("tag", "db_compute=" + db_compute);

        }

        return c1;
    }


}
