package team13.nim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import static java.lang.Thread.sleep;

public class Gameplay extends AppCompatActivity {

    String username = null;
    GamePiece gps[] = new GamePiece[28];
    int wait_mult = 1;
    int currentRow;
    int winnable;
    int [] gameBoard = new int[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        Intent intent = getIntent();

        //gets the values from the parent activity
        int numRows = intent.getIntExtra(Configurations.NUMROWS, 0);
        int whoStarts = intent.getIntExtra(Configurations.STARTPLAYER, 0);
        int difficulty = intent.getIntExtra(Configurations.DIFFICULTY, 0);
        username = intent.getStringExtra(Configurations.PLAYERNAME);


        buildBoard();

        makeArray();

        if(numRows == 6)
        {
            for(int i=21;i<28;i++)
                gps[i].setVisibility(View.INVISIBLE);
        }
        else if( numRows == 5)
        {
            for(int i = 15; i<28;i++)
                gps[i].setVisibility(View.INVISIBLE);
        }

        if (whoStarts == 1)
        {
            int [] move = new int[2];
            int cntr = 0;
            if (numRows == 5)
               winnable = 15;
            if (numRows == 6)
                winnable = 21;
            if (numRows == 7)
                winnable = 28;
            if (difficulty == 1)
                move = casualMove();
            if (difficulty == 2)
                move = hardcoreMove();
            currentRow = move[0];
            cntr = move[1];
            gameBoard[currentRow] = gameBoard[currentRow] - cntr;
            winnable = winnable - cntr;
            for (int j = 0; j < 28; j++)
            {
                if ((gps[j].getRow() == currentRow) && (gps[j].getColor() == 0) && (cntr > 0))
                {
                    gps[j].chngColor(2);
                    cntr--;
                }
            }
        }

    }

    public void backToHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void waitTime(View view)
    {
        wait_mult++;
    }

    public void remove(View view)
    {
        int selected = 0;
        int i =0;
        while(i<28&&selected ==0)
        {
            if(gps[i].getColor()==1)
                selected = 1;
            i++;
        }
        if(selected == 0)
        {
            Toast.makeText(getBaseContext(), "No Pieces Selected" , Toast.LENGTH_SHORT ).show();
            return;
        }

        Intent data = getIntent();
        int cntr = 0;
        int [] move = new int[2];
        for (i = 0; i < 28; i++)
        {
            if (gps[i].getColor() == 1)
            {
                cntr++;
                gps[i].chngColor(2);
            }
        }
        //add code here to change gp color to removed
       gameBoard[currentRow] = gameBoard[currentRow] - cntr;
        winnable = winnable - cntr;
        try {
            sleep(wait_mult * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (data.getIntExtra(Configurations.DIFFICULTY, 0) == 1)
            move = casualMove();
        if (data.getIntExtra(Configurations.DIFFICULTY, 0) == 2) {
            move = hardcoreMove();
        }
        currentRow = move[0];
        cntr = move[1];
        gameBoard[currentRow] = gameBoard[currentRow] - cntr;
        winnable = winnable - cntr;
        for (int j = 0; j < 28; j++)
        {
            if ((gps[j].getRow() == currentRow) && (gps[j].getColor() == 0) && (cntr > 0))
            {
                gps[j].chngColor(2);
                cntr--;
            }
        }
        if (winnable == 0 && data.getBooleanExtra(Configurations.KEEP_PLAYING, false) == true)
        {
            finish();
            startActivity(data);
        }
        if (winnable == 0 && data.getBooleanExtra(Configurations.KEEP_PLAYING, false) == false)
        {
            finish();
            backToHome(view);
        }
    }

    public void select(View view)
    {

        //add logic to stop illegal selection
        GamePiece gp = (GamePiece) view;
        if(gp.getColor() == 2)
            return;

        currentRow = gp.getRow();

        for(int i=0; i<28; i++)
        {
            if(gps[i].getRow() != currentRow && gps[i].getColor() != 2)
                gps[i].chngColor(0);
        }

        //add code to change color to selected
        gp.chngColor(1);
    }

    public int[] casualMove()
    {
        Intent data = getIntent();
        Random random = new Random();
        int row = random.nextInt(data.getIntExtra(Configurations.NUMROWS, 0));
        while (gameBoard[row] == 0)
        {
            row = random.nextInt(data.getIntExtra(Configurations.NUMROWS, 0));
        }
        int remove;
        if (gameBoard[row] == 1)
        {
            remove = 1;
        }
        else
        {
            remove = random.nextInt(gameBoard[row]) + 1;
        }
        int [] move = {row, remove};
        return move;
    }

    public int[] hardcoreMove()
    {
        Intent data = getIntent();
        int nimSum = calcNimSum(gameBoard);
        int [] temp = gameBoard.clone();
        int [] move = new int[2];
        if (nimSum == 0)
        {
            move = casualMove();
            return move;
        }
        for (int k = 0; k < data.getIntExtra(Configurations.NUMROWS, 0); k++)
        {
            temp[k] = temp[k] - nimSum;
            if (calcNimSum(temp) == 0)
                {
                    //correct move
                    move[0] = k;
                    move[1] = nimSum;
                    return move;
                }
            else
                temp = gameBoard.clone();
        }
        if (move[0] == 0 && move[1] == 0)
        {
            move = casualMove();
            return move;
        }
        return null;
    }

    public int calcNimSum(int [] arr)
    {
        Intent data = getIntent();
        int ret = 0;
        for (int j = 0; j < data.getIntExtra(Configurations.NUMROWS, 0); j++)
        {
            ret = ret ^ arr[j];
        }
        return ret;
    }

    void buildBoard() {
        Intent data = getIntent();
        for (int i = 0; i < data.getIntExtra(Configurations.NUMROWS, 0); i++) {
            gameBoard[i] = i + 1;
        }
    }

    public void makeArray()
    {
        gps[0] = (GamePiece) findViewById(R.id.gp1);
        gps[1] = (GamePiece) findViewById(R.id.gp21);
        gps[2] = (GamePiece) findViewById(R.id.gp22);
        gps[3] = (GamePiece) findViewById(R.id.gp31);
        gps[4] = (GamePiece) findViewById(R.id.gp32);
        gps[5] = (GamePiece) findViewById(R.id.gp33);
        gps[6] = (GamePiece) findViewById(R.id.gp41);
        gps[7] = (GamePiece) findViewById(R.id.gp42);
        gps[8] = (GamePiece) findViewById(R.id.gp43);
        gps[9] = (GamePiece) findViewById(R.id.gp44);
        gps[10] = (GamePiece) findViewById(R.id.gp51);
        gps[11] = (GamePiece) findViewById(R.id.gp52);
        gps[12] = (GamePiece) findViewById(R.id.gp53);
        gps[13] = (GamePiece) findViewById(R.id.gp54);
        gps[14] = (GamePiece) findViewById(R.id.gp55);
        gps[15] = (GamePiece) findViewById(R.id.gp61);
        gps[16] = (GamePiece) findViewById(R.id.gp62);
        gps[17] = (GamePiece) findViewById(R.id.gp63);
        gps[18] = (GamePiece) findViewById(R.id.gp64);
        gps[19] = (GamePiece) findViewById(R.id.gp65);
        gps[20] = (GamePiece) findViewById(R.id.gp66);
        gps[21] = (GamePiece) findViewById(R.id.gp71);
        gps[22] = (GamePiece) findViewById(R.id.gp72);
        gps[23] = (GamePiece) findViewById(R.id.gp73);
        gps[24] = (GamePiece) findViewById(R.id.gp74);
        gps[25] = (GamePiece) findViewById(R.id.gp75);
        gps[26] = (GamePiece) findViewById(R.id.gp76);
        gps[27] = (GamePiece) findViewById(R.id.gp77);

        gps[0].setRow(0);
        gps[1].setRow(1);
        gps[2].setRow(1);
        gps[3].setRow(2);
        gps[4].setRow(2);
        gps[5].setRow(2);
        gps[6].setRow(3);
        gps[7].setRow(3);
        gps[8].setRow(3);
        gps[9].setRow(3);
        gps[10].setRow(4);
        gps[11].setRow(4);
        gps[12].setRow(4);
        gps[13].setRow(4);
        gps[14].setRow(4);
        gps[15].setRow(5);
        gps[16].setRow(5);
        gps[17].setRow(5);
        gps[18].setRow(5);
        gps[19].setRow(5);
        gps[20].setRow(5);
        gps[21].setRow(6);
        gps[22].setRow(6);
        gps[23].setRow(6);
        gps[24].setRow(6);
        gps[25].setRow(6);
        gps[26].setRow(6);
        gps[27].setRow(6);
    }


}
