package team13.nim;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;



@Dao
public interface UserDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addUser(UserData username);

    @Update
    public void addGame(UserData username);

    @Query("SELECT * FROM UserData WHERE username LIKE :name")
    public UserData getUser(String name);
}
