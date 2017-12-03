package team13.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.*;

public class Gameplay extends AppCompatActivity {

    Intent intent = getIntent();

    //gets the values from the parent activity
    int numRows = intent.getIntExtra(Configurations.NUMROWS, 0);
    int whoStarts = intent.getIntExtra(Configurations.STARTPLAYER, 0);
    int difficulty = intent.getIntExtra(Configurations.DIFFICULTY, 0);
    String username = intent.getStringExtra(Configurations.PLAYERNAME);
    int wait = 1;

    int [] gameBoard = new int[numRows];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        buildBoard();




    }

    public void backToHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void waitTime(View view)
    {
        wait++;
    }

    public void remove(View view)
    {
        //add code here to change gp color to removed

        if (difficulty == 0)
            casualMove();
        if (difficulty == 1)
            hardcoreMove();

    }

    public void select(View view)
    {
        //add logic to stop illegal selection
        //add code to change color to selected
    }

    public int[] casualMove()
    {
        Random random = new Random();
        int row = random.nextInt(numRows + 1 - 0) + 0;
        int remove = random.nextInt(gameBoard[row] + 1 - 1) + 1;
        int [] move = {row, remove};
        return move;
    }

    public int[] hardcoreMove()
    {
        int nimSum = calcNimSum(gameBoard);
        int [] temp = gameBoard.clone();
        int [] move = new int[2];
        if (nimSum == 0)
            casualMove();
        for (int k = 0; k < numRows; k++)
        {
            temp[k] = temp[k] - nimSum;
            if (calcNimSum(temp) == 0)
                {
                    //correct move
                    move = new int[]{k, nimSum};
                    break;
                }
            else
                temp = gameBoard.clone();
        }
        return move;
    }

    public int calcNimSum(int [] arr)
    {
        int ret = 0;
        for (int j = 0; j < numRows; j++)
        {
            ret = ret ^ gameBoard[j];
        }
        return ret;
    }

    void buildBoard() {
        for (int i = 0; i < numRows; i++) {
            switch (i) {
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
    }




}
