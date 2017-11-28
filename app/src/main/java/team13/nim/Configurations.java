package team13.nim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;



public class Configurations extends AppCompatActivity {

    public static final String STARTPLAYER = "com.team13.nim.player";
    public static final String NUMROWS = "com.team13.nim.numrows";
    public static final String PLAYERNAME = "com.team13.nim.username";
    public static final String DIFFICULTY = "com.team13.nim.difficulty";

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);

        //get the intent that created the activity
        Intent intent = getIntent();

        //now this activity has the username
        username = intent.getStringExtra(MainActivity.USERNAME);


        //String username = intent.getStringExtra(MainActivity.username);


        if(!(MainActivity.get().is_guest()))
        {
            final UserData user = new UserData();

            user.setLosses(0);
            user.setWins(0);
            user.setUsername(username);

            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    MainActivity.get().getDB().userDao().addUser(user);
                }
            }).start();
        }



    }
    public void startGame(View view) {
        //creates the intent with the configurations class
        Intent intent = new Intent(this, Gameplay.class);


        int whoStarts;
        int numRows;
        int difficulty;

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
