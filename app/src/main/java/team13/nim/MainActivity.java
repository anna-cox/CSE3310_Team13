package team13.nim;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {
    //private EditText user_name;
    private Button Button;
    private Button Guest;
    static String username;
    boolean is_guest = false;

    public static MainActivity THIS;


    public static final String USERNAME = "com.team13.Nim.MESSAGE";

    private static userDatabase db = null;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Guest = (Button)findViewById(R.id.play_as_guest);

        Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_guest = true;
                Intent intent = new Intent(MainActivity.this, Configurations.class);
                startActivity(intent);
            }
        });


        THIS = this;
    }


    public static MainActivity get()
    {
        return THIS;
    }

    public userDatabase getDB()
    {
            if(db == null)
                db = Room.databaseBuilder(getApplicationContext(), userDatabase.class, "Database-Name").build();

            return db;

    }

    public void validate(View view)
    {
        EditText user_name = (EditText)findViewById(R.id.username);
        username = user_name.getText().toString();

        if(username!=null && username.length()==0)
        {
            Toast.makeText(getBaseContext(), "Enter username" , Toast.LENGTH_SHORT ).show();
        }
        else
        {
            Intent intent = new Intent(MainActivity.this, Configurations.class);
            intent.putExtra(USERNAME, username);
            startActivity(intent);
        }
    }

    public static String username_getter()
    {
        return username;
    }

    public boolean is_guest() {return is_guest;}

}
