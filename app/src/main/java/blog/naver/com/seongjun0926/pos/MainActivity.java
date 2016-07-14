package blog.naver.com.seongjun0926.pos;

import android.app.Activity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
    SQLiteDatabase db;
    MENUSQLiteOpenHelper helper;
    int open = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        helper = new MENUSQLiteOpenHelper(MainActivity.this, "MENU_TABLE_DATABASE.db", null, 1);

        db = helper.getReadableDatabase();
        Log.i("tag", "db=" + db);

        Cursor c = db.rawQuery("SELECT * FROM MENU", null);
        open = c.getCount();
        Log.i("tag", "_id=" + open);

        Button Menu_Setting = (Button) findViewById(R.id.Menu_Setting);
        Menu_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i);

            }
        });

        Button Table_Setting = (Button) findViewById(R.id.Table_Setting);
        Table_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.rawQuery("SELECT * FROM MENU", null);
                open = c.getCount();
                
                Log.i("tag", "_id=" + open);
                if (open >= 1) {
                    Intent i = new Intent(getApplicationContext(), TableActivity.class);
                    startActivity(i);
                } else
                    Toast.makeText(MainActivity.this, "메뉴 설정을 먼저 해주십시오.", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
