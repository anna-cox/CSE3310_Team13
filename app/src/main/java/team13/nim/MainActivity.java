package team13.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText user_name;
    private Button Button;
    private Button Guest;
    static String username;
    boolean is_guest = false;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name = (EditText)findViewById(R.id.username);
        Button = (Button)findViewById(R.id.startpage_button);
        username = user_name.getText().toString();
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(user_name.getText().toString());
            }
        }
        );

        Guest = (Button)findViewById(R.id.play_as_guest);
        Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_guest = true;
                Intent intent = new Intent(MainActivity.this, Configurations.class);
                startActivity(intent);
            }
        })
        ;


    }
    private void validate(String user_name)
    {
        if(user_name!=null && user_name.length()==0)
        {
            Toast.makeText(getBaseContext(), "Enter username" , Toast.LENGTH_SHORT ).show();
        }
            else
            {
                Intent intent = new Intent(MainActivity.this, Configurations.class);
                startActivity(intent);
            }
    }
    public static String username_getter()
    {
        return username;
    }

}
