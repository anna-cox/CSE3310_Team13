package team13.nim;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.v7.app.AppCompatActivity;



@Database(entities = {UserData.class}, version = 1)
public abstract class userDatabase extends RoomDatabase
{

   public abstract UserDao userDao();
}
