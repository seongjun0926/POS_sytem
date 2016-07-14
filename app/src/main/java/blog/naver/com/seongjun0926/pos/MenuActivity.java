package blog.naver.com.seongjun0926.pos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static blog.naver.com.seongjun0926.pos.MainActivity.*;

public class MenuActivity extends Activity {
    SQLiteDatabase db;
    MENUSQLiteOpenHelper helper;
    private static String TABLE_NAME = "MENU";

    private String MENU=null;
    private String PRICE=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.i("tag", "onCreate");

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        helper = new MENUSQLiteOpenHelper(MenuActivity.this, "MENU_TABLE_DATABASE.db", null, 1);

        Button MenuCreateBtn = (Button) findViewById(R.id.MenuCreateBtn);
        MenuCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("tag", "MenuCreateBtn");
                EditText name_ET = (EditText) findViewById(R.id.name_ET);
                EditText price_ET = (EditText) findViewById(R.id.price_ET);

                MENU = name_ET.getText().toString();
                PRICE = price_ET.getText().toString();

                Log.i("tag", "MENU = " + MENU + "," + "PRICE = " + PRICE);

                AlertDialog dialog = createDialogBox();
                dialog.show();
            }
        });


    }

    private void showDatabase() {
        Log.i("tag", "insert");

        ListView list = (ListView) findViewById(R.id.listView);

        boolean isOpen = openDatabase();
        if (isOpen) {
            Cursor cursor = executeRawQueryParam();
            startManagingCursor(cursor);

            String[] columns = new String[]{"name", "price"};
            int[] to = new int[]{R.id.name_entry, R.id.price_entry};

            final SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.listitem, cursor, columns, to);

            list.setAdapter(mAdapter);
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //길게누르면 메뉴 삭제 리스너
                    Log.i("tag", "i=" + i);

                    Cursor c = (Cursor) mAdapter.getItem(i);

                    final String id = c.getString(i -= i);
                    Log.i("tag", "id=" + id);


                    AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);

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
        while (c1.moveToNext()) {
            String db_name = c1.getString(c1.getColumnIndex("name"));
            String db_price = c1.getString(c1.getColumnIndex("price"));
            Log.i("tag", "db_name=" + db_name);
            Log.i("tag", "db_price=" + db_price);

        }

        return c1;
    }


    private AlertDialog createDialogBox() {
        Log.i("tag", "createDialogBox");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("안내");
        builder.setMessage("메뉴 등록 하시겠습니까?");
        builder.setIcon(R.drawable.abc_list_divider_mtrl_alpha);

        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               if(MENU.length()!=0 && PRICE.length()!=0) {
                   //아무것도 입력을 받지 않았을 때가 아닐때.
                   insert(MENU, PRICE);


               }
               else
                   Toast.makeText(MenuActivity.this,"올바른 입력을 하세요.",Toast.LENGTH_SHORT).show();
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


    public void insert(String name, String price) {
        Log.i("tag", "insert");
        Log.i("tag", "db=" + db);

        db = helper.getWritableDatabase();
        Log.i("tag", "db=" + db);

        Log.i("tag", "insert\nname=" + name + ",price=" + price);
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);

        db.insert(TABLE_NAME, null, values);


        showDatabase();
    }

    public void delete(String name) {
        db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[]{name});

        showDatabase();
    }
}