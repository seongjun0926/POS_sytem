package blog.naver.com.seongjun0926.pos;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TableActivity extends Activity implements OnMenuItemClickListener {

    MENUSQLiteOpenHelper helper;
    private static String TABLE_NAME;
    int open;
    SQLiteDatabase db;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);


        helper = new MENUSQLiteOpenHelper(TableActivity.this, "MENU_DATABASE.db", null, 1);
        db = helper.getReadableDatabase();




        ImageButton Table_Button_1 = (ImageButton) findViewById(R.id.Table_1);
        ImageButton Table_Button_2 = (ImageButton) findViewById(R.id.Table_2);
        ImageButton Table_Button_3 = (ImageButton) findViewById(R.id.Table_3);
        ImageButton Table_Button_4 = (ImageButton) findViewById(R.id.Table_4);
        ImageButton Table_Button_5 = (ImageButton) findViewById(R.id.Table_5);
        ImageButton Table_Button_6 = (ImageButton) findViewById(R.id.Table_6);
        ImageButton Table_Button_7 = (ImageButton) findViewById(R.id.Table_7);
        ImageButton Table_Button_8 = (ImageButton) findViewById(R.id.Table_8);
        ImageButton Table_Button_9 = (ImageButton) findViewById(R.id.Table_9);
        ImageButton Table_Button_10 = (ImageButton) findViewById(R.id.Table_10);
        ImageButton Table_Button_11 = (ImageButton) findViewById(R.id.Table_11);
        ImageButton Table_Button_12 = (ImageButton) findViewById(R.id.Table_12);

        intent = new Intent(this, Table_Menu_Activity.class);

        Table_Button_1.setOnClickListener(i);

        Table_Button_2.setOnClickListener(i);
        Table_Button_3.setOnClickListener(i);
        Table_Button_4.setOnClickListener(i);
        Table_Button_5.setOnClickListener(i);
        Table_Button_6.setOnClickListener(i);
        Table_Button_7.setOnClickListener(i);
        Table_Button_8.setOnClickListener(i);
        Table_Button_9.setOnClickListener(i);
        Table_Button_10.setOnClickListener(i);
        Table_Button_11.setOnClickListener(i);
        Table_Button_12.setOnClickListener(i);


    }

    View.OnClickListener i = new View.OnClickListener() {
        //팝업 버튼 리스너
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.Table_1:
                    TABLE_NAME = "TABLE_MENU_1";
                    break;
                case R.id.Table_2:
                    TABLE_NAME = "TABLE_MENU_2";
                    break;
                case R.id.Table_3:
                    TABLE_NAME = "TABLE_MENU_3";
                    break;
                case R.id.Table_4:
                    TABLE_NAME = "TABLE_MENU_4";
                    break;
                case R.id.Table_5:
                    TABLE_NAME = "TABLE_MENU_5";
                    break;
                case R.id.Table_6:
                    TABLE_NAME = "TABLE_MENU_6";
                    break;
                case R.id.Table_7:
                    TABLE_NAME = "TABLE_MENU_7";
                    break;
                case R.id.Table_8:
                    TABLE_NAME = "TABLE_MENU_8";
                    break;
                case R.id.Table_9:
                    TABLE_NAME = "TABLE_MENU_9";
                    break;
                case R.id.Table_10:
                    TABLE_NAME = "TABLE_MENU_10";
                    break;
                case R.id.Table_11:
                    TABLE_NAME = "TABLE_MENU_11";
                    break;
                case R.id.Table_12:
                    TABLE_NAME = "TABLE_MENU_12";
                    break;

            }

            PopupMenu popup = new PopupMenu(TableActivity.this, view);
            getMenuInflater().inflate(R.menu.menu_table, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(TableActivity.this);
        }
    };


    public boolean onMenuItemClick(final MenuItem item) {
        Log.i("tag", "onMenuItemClick");
        Log.i("tag", "item=" + item);

        switch (item.getItemId()) {

            case R.id.menu_add:

                Intent i = new Intent(getApplicationContext(), Table_Select_Activity.class);
                i.putExtra("TABLE_NAME", TABLE_NAME);
                startActivity(i);
                finish();
                break;

            case R.id.menu_show:

                Intent i2 = new Intent(getApplicationContext(), Table_Menu_Activity.class);
                i2.putExtra("TABLE_NAME", TABLE_NAME);
                startActivity(i2);


                break;
            case R.id.menu_compute:


                Intent i3 = new Intent(getApplicationContext(), ComputeActivity.class);
                i3.putExtra("TABLE_NAME", TABLE_NAME);
                startActivity(i3);

        }

        return true;
    }



}
