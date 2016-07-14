package blog.naver.com.seongjun0926.pos;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ComputeActivity extends Activity {
    private static String TABLE_NAME;

    MENUSQLiteOpenHelper helper;
    SQLiteDatabase db;
    int db_sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        Log.i("tag", "onCreate");
        Intent i = getIntent();
        TABLE_NAME = i.getStringExtra("TABLE_NAME");
        Log.i("tag", "TABLE_NAME=" + TABLE_NAME);

       helper = new MENUSQLiteOpenHelper(ComputeActivity.this, "MENU_TABLE_DATABASE.db", null, 1);
        db = openOrCreateDatabase("MENU_TABLE_DATABASE.db", MODE_WORLD_WRITEABLE, null);
        showDatabase();


            int db_input_sum = sum();

         //   put(db_input_sum);

        Button rest_BTN =(Button)findViewById(R.id.reset);
        rest_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = createDialogBox();
                dialog.show();
            }
        });
    }


    private int sum( ) {
        Log.i("tag", "sum");
        db = helper.getReadableDatabase();
        Log.i("tag", "db=" + db);

        Cursor c = db.rawQuery("select * from "+TABLE_NAME, null);

        while (c.moveToNext()) {
            int compute = c.getInt(c.getColumnIndex("compute"));
         /*   int _id=c.getInt(c.getColumnIndex("_id"));
            String b=c.getString(c.getColumnIndex("name"));
            String d=c.getString(c.getColumnIndex("price"));
            String e=c.getString(c.getColumnIndex("ea"));
            String f=c.getString(c.getColumnIndex("computer"));
            String g=c.getString(c.getColumnIndex("sum"));*/

            db_sum += compute;
       /*     Log.i("id", "_id=" + _id);
            Log.i("name", "name=" + b);
            Log.i("price", "price=" + d);
            Log.i("ea", "ea=" + e);
            Log.i("computer", "computer=" + f);
            Log.i("sum", "sum=" + g);*/




        }

        Log.i("tag", "db_sum=" + db_sum);

        return db_sum;
    }
    private AlertDialog createDialogBox() {
        Log.i("tag", "createDialogBox");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(db_sum+"원");
        builder.setMessage("계산 하시겠습니까??");
        builder.setIcon(R.drawable.abc_list_divider_mtrl_alpha);

        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                delete();
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton) {
            }
        });

        AlertDialog dialog = builder.create();

        return dialog;
    }



    private void showDatabase() {
        ListView list = (ListView) findViewById(R.id.listView4);

        boolean isOpen = openDatabase();
        if (isOpen) {
            Cursor cursor = executeRawQueryParam();
            startManagingCursor(cursor);

            String[] columns = new String[]{"name", "price", "compute","ea"};
            int[] to = new int[]{R.id.dataItem01, R.id.dataItem02, R.id.dataItem03, R.id.number_entry2};

            final SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.listitem_reset, cursor, columns, to);

            list.setAdapter(mAdapter);
        }
    }

    private boolean openDatabase() {
        Log.i("tag", "openDatabase");

        return true;
    }
    private Cursor executeRawQueryParam() {
        Log.i("tag", "executeRawQueryParam");

        String SQL = "select * from "+TABLE_NAME;

        Log.i("tag", "SQL=" + SQL);

        Cursor c1 = db.rawQuery(SQL, null);
        while (c1.moveToNext()) {
            db_sum = c1.getInt(c1.getColumnIndex("sum"));
            Log.i("tag", "db_sum="+db_sum);

        }
        return c1;
    }

    public void delete() {
        db = helper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        Toast.makeText(this,"계산이 완료되었습니다.",Toast.LENGTH_SHORT).show();
        finish();

        String sql = "create table "+TABLE_NAME+" ( _id integer primary key autoincrement, name text, price integer, ea integer,  compute integer, sum integer);";

        db.execSQL(sql);

    }

}
