package team13.nim;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity
public class UserData
{

    @NotNull
    @PrimaryKey
    private String username;

    private int wins;
    private int losses;



    public int getWins(){return wins;}
    public int getLosses(){return losses;}
    public void setWins(int _wins){wins = _wins;}
    public void setLosses(int _losses){losses = _losses;}
    public void setUsername(String name){username = name;}
    public String getUsername(){return username;}


}
