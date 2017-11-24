package team13.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Configurations extends AppCompatActivity {

    public static final String STARTPLAYER = "com.example.myfirstapp.player";
    public static final String NUMROWS = "com.example.myfirstapp.numrows";
    public static final String DIFFICULTY = "com.example.myfirstapp.difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);

        //get the intent that created the activity
        Intent intent = getIntent();

        //now this activity has the username
        String username = intent.getStringExtra(MainActivity.username);

    }


    public void startGame(View view) {
        //creates the intent with the configurations class
        Intent intent = new Intent(this, Gameplay.class);


        int whoStarts;
        int numRows;
        int difficulty;  //1 for easy, 2 for medium, and 3 for hard.

        //gets the start player from the radio buttons
        if (((RadioButton) findViewById(R.id.computerStart)).isChecked()) {
            whoStarts = 1;
        } else {
            whoStarts = 0;
        }

        //gets the number of rows from the radio button
        if (((RadioButton) findViewById(R.id.rows5)).isChecked()) {
            numRows = 5;
        } else if (((RadioButton) findViewById(R.id.rows6)).isChecked()) {
            numRows = 6;
        } else {
            numRows = 7;
        }

        //gets the difficulty level .....1 for easy, 2 for medium and 3 for hard.
        if (((RadioButton) findViewById(R.id.easy)).isChecked())
            difficulty=1;
        else if (((RadioButton) findViewById(R.id.medium)).isChecked())
            difficulty=2;
        else difficulty=3;

        //attaches the values to the activity
        intent.putExtra(STARTPLAYER, whoStarts);
        intent.putExtra(NUMROWS, numRows);
        intent.putExtra(DIFFICULTY, difficulty);


        //starts the next activity
        startActivity(intent);

    }


}
