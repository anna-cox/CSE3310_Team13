package team13.nim;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.*;

import static java.lang.Thread.sleep;

public class Gameplay extends AppCompatActivity {

    public static final String STARTPLAYER = "com.team13.nim.playerGP";
    public static final String NUMROWS = "com.team13.nim.numrowsGP";
    public static final String PLAYERNAME = "com.team13.nim.usernameGP";
    public static final String DIFFICULTY = "com.team13.nim.difficultyGP";

    String username = null;
    GamePiece gps[] = new GamePiece[28];
    int wait_mult = 1;
    int currentRow;
    int winnable = 28;
    int [] gameBoard = new int[7];
    int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        Intent intent = getIntent();


        int numRows = intent.getIntExtra(Configurations.NUMROWS, 0);
        int whoStarts = intent.getIntExtra(Configurations.STARTPLAYER, 0);
        int difficulty = intent.getIntExtra(Configurations.DIFFICULTY, 0);
        username = intent.getStringExtra(Configurations.PLAYERNAME);

        // initialize array maintaining number of pieces left on each row for AI to use
        buildBoard();
        // initialize array maintaining the game pieces themselves (for color changing)
        makeArray();
        // Don't show 7th row if user chooses 6
        if(numRows == 6)
        {
            for(int i=21;i<28;i++)
                gps[i].setVisibility(View.INVISIBLE);
            winnable = 21;
        }
        // Don't show 6th and 7th row if user chooses 5
        else if( numRows == 5)
        {
            for(int i = 15; i<28;i++)
                gps[i].setVisibility(View.INVISIBLE);
            winnable = 15;
        }
        else
            winnable = 28;

        // If the AI is to start the game, perform their first move with original initialization
        if (whoStarts == 1)
        {
            int [] move = new int[2];
            int cntr = 0;
            // If easy, random move
            if (difficulty == 1)
                move = casualMove();
            // If hard, nimSum move
            if (difficulty == 2)
                move = hardcoreMove();
            // Grab row from AI move array
            currentRow = move[0];
            // Grab # to remove from AI move array
            cntr = move[1];
            // Update AI array with new # of pieces on said row
            gameBoard[currentRow] = gameBoard[currentRow] - cntr;
            // Decrease total number of pieces left
            winnable = winnable - cntr;
            // Actually perform the move, changing the colors of the pieces
            for (int j = 0; j < 28; j++)
            {
                if ((gps[j].getRow() == currentRow) && (gps[j].getColor() == 0) && (cntr > 0))
                {
                    gps[j].chngColor(2);
                    cntr--;
                }
            }
            turn = 0;
        }

    }

    public void backToHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    // If user presses slow down, increment the sleep() multiplier
    public void waitTime(View view)
    {
        wait_mult++;
    }

    // Function to handle action of player turn and call AI turn
    public void remove(View view)
    {
        //if no pieces are selected then give the user a notification and don't remove anything
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
        // Remove pieces selected by the player
        for (i = 0; i < 28; i++)
        {
            if (gps[i].getColor() == 1)
            {
                // Total # of pieces removed by player for updating AI array
                cntr++;

                gps[i].chngColor(2);

            }
        }


        winnable = winnable - cntr;
        keepGoing();
        if(winnable == 0)
            return;


        turn = 1;
        // Update AI array with new piece # for said row
       gameBoard[currentRow] = gameBoard[currentRow] - cntr;
        // Delay between end of Player turn and beginning of next Player turn as AI performs move
        try {
            for (int l = 0; l < 10; l++) {
                sleep(wait_mult * 100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // If easy, random AI move
        if (data.getIntExtra(Configurations.DIFFICULTY, 0) == 1)
            move = casualMove();
        // If hard, nimSum AI move
        if (data.getIntExtra(Configurations.DIFFICULTY, 0) == 2) {
            move = hardcoreMove();
        }
        // grab row from AI move
        currentRow = move[0];
        // grab # to remove from AI move
        cntr = move[1];
        // update AI array with # pieces remaining on said row
        gameBoard[currentRow] = gameBoard[currentRow] - cntr;
        // update total # pieces left in game
        winnable = winnable - cntr;
        // change color of pieces actually removed by AI
        for (int j = 0; j < 28; j++)
        {
            if ((gps[j].getRow() == currentRow) && (gps[j].getColor() == 0) && (cntr > 0))
            {
                gps[j].chngColor(2);
                cntr--;
            }
        }
        keepGoing();
        if(winnable == 0)
            return;


        turn = 0;

    }

    public void select(View view)
    {

        GamePiece gp = (GamePiece) view;
        // if piece is already removed, do nothing
        if(gp.getColor() == 2)
            return;
        // if piece is already selected, de-select
        if (gp.getColor() == 1)
        {
            gp.chngColor(0);
            return;
        }
        // assume current row is the row of the most recently selected piece
        currentRow = gp.getRow();

        // de-selects all pieces not on currentRow
        for(int i=0; i<28; i++)
        {
            if(gps[i].getRow() != currentRow && gps[i].getColor() != 2)
                gps[i].chngColor(0);
        }
        // changes the color of clicked piece to indicate selection
        gp.chngColor(1);
    }

    // randomizes the AI move by choosing a random row, and random number of pieces to remove from that row.
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

    // calculates the AI move based on the nimSum (exclusive or of # pieces in each row)
    public int[] hardcoreMove()
    {
        Intent data = getIntent();
        int nimSum = calcNimSum(gameBoard);
        int [] temp = gameBoard.clone();
        int [] move = new int[2];
        // if nimSum already is 0, any move will do, so randomize
        if (nimSum == 0)
        {
            move = casualMove();
            return move;
        }
        // otherwise, test each row to see where nimSum pieces can be removed, and result in a re-calculated nimSum of 0 [this is considered the optimal move]
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
            // if since we removed nimSum pieces from row, we need to make sure next attempt has a fresh array
            else
                temp = gameBoard.clone();
        }
        // if somehow no row and # is selected, randomize move
        if (move[0] == 0 && move[1] == 0)
        {
            move = casualMove();
            return move;
        }
        return null;
    }

    // Calculate nimSum (exclusive or) of # pieces in each row; this result gives the # of pieces needed to be removed for the optimal turn
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

    // builds the array to represent the number of pieces on each row for AI use
    void buildBoard() {
        Intent data = getIntent();
        for (int i = 0; i < data.getIntExtra(Configurations.NUMROWS, 0); i++) {
            gameBoard[i] = i + 1;
        }
    }
    //determines if the game has been completeted and if so who the winner is based on turn
    //if a username was provided, adds either a win or a loss to the database

    // maintains and tests end-game scenarios
    void keepGoing()
    {
        //if there are no pieces left
        if(winnable==0)
        {

            //notification telling the user that they won or lost
            if(turn == 0)
                Toast.makeText(getBaseContext(), "You Lost", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getBaseContext(), "You Won", Toast.LENGTH_SHORT).show();

            //if username provided, add win or loss to the database
            if(username != null)
            {


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        UserData player = MainActivity.get().getDB().userDao().getUser(username);
                        if (turn == 0) {

                            int losses = player.getLosses();
                            losses++;
                            player.setLosses(losses);

                        } else {

                            int wins = player.getWins();
                            wins++;
                            player.setWins(wins);

                        }

                        MainActivity.get().getDB().userDao().addGame(player);


                    }
                }).start();
            }



            boolean keep = MainActivity.get().keepPlaying();
            //if keep playing option was selected, restart the gameplay activity
            if (keep)
            {


                Intent intent = getIntent();

                int numRows = intent.getIntExtra(Configurations.NUMROWS, 0);
                int whoStarts = intent.getIntExtra(Configurations.STARTPLAYER, 0);
                int difficulty = intent.getIntExtra(Configurations.DIFFICULTY, 0);

                intent.putExtra(STARTPLAYER, whoStarts);
                intent.putExtra(NUMROWS, numRows);
                intent.putExtra(DIFFICULTY, difficulty);
                intent.putExtra(PLAYERNAME, username);

                finish();
                startActivity(intent);

            }
            //if play once was selected, start the main activity to go to the homepage
            if (!keep)
            {
                Intent intent = new Intent(this, MainActivity.class);

                finish();
                startActivity(intent);
            }
        }


    }

    // creates array of gamePieces (for changing colors)
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
