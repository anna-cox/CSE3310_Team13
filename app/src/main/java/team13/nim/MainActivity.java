package team13.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //the "key" so the intent knows what string we are talking about, kinda like a variable name
    //so it can be referenced from multiple activities
    public static final String USERNAME = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //creates a new activity
    //attached to the start button on the start page
    public void startSettings(View view)
    {
        //creates the intent with the configurations class
        Intent intent = new Intent(this, Configurations.class);
        //gets the textbox
        EditText editText = (EditText) findViewById(R.id.username);
        //gets the string from the textbox
        String message = editText.getText().toString();
        //adds the string to the intent
        intent.putExtra(USERNAME, message);
        //starts the next activity
        startActivity(intent);

    }
}
