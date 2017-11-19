package team13.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.*;

public class Gameplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);


        Intent intent = getIntent();

        //getse the values from the parent activity
        int numRows = intent.getIntExtra(Configurations.NUMROWS, 0);
        int whoStarts = intent.getIntExtra(Configurations.STARTPLAYER, 0);

        int [] gameBoard = new int[numRows];
        for (int i = 0; i < numRows; i++)
        {
            switch(i) {
                case 0:
                    gameBoard[i] = 0b0001;
                    break;
                case 1:
                    gameBoard[i] = 0b0010;
                    break;
                case 2:
                    gameBoard[i] = 0b0011;
                    break;
                case 3:
                    gameBoard[i] = 0b0100;
                    break;
                case 4:
                    gameBoard[i] = 0b0101;
                    break;
                case 5:
                    gameBoard[i] = 0b0110;
                    break;
                case 6:
                    gameBoard[i] = 0b0111;
                    break;
            }
        }

        //uses those values

        TextView rows = (TextView)findViewById(R.id.numRows);
        rows.setText(Integer.toString(numRows));


        TextView player = (TextView)findViewById(R.id.startPlayer);
        if(whoStarts == 0)
            player.setText("You");
        else
            player.setText("Computer");
    }

    public void backToHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
/*
    public int[] casualMove()
    {
        Random random = new Random();
        int row = random.nextInt(numRows + 1 - 0) + 0;
        int remove = random.nextInt(gameBoard[row] + 1 - 1) + 1;
        int [] move = {row, remove};
    }

    public int[] hardcoreMove()
    {
        int nimSum = calcNimSum(gameBoard);
        int [] temp = gameBoard.clone();
        for (int k = 0; k < numRows; k++)
        {
            temp[k] = temp[k] - nimSum;
            if (calcNimSum == 0)
                {
                    //correct move
                    int [] move = {k, nimSum};
                    break;
                }
            else
                temp = gameBoard.clone
        }
    }

    public int calcNimSum(int [] arr)
    {
        int ret = 0;
        for (int j = 0; j < numRows; j++)
        {
            ret = ret ^ gameBoard[j];
        }
    }
*/
}
