package team13.nim;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Addison on 11/29/2017.
 */
//@TargetApi(11)
public class GamePiece extends android.support.v7.widget.AppCompatButton {

    int color; //0=unselected, 1=selected, 2=removed

    public GamePiece(Context context)
    {
        super(context);
    }
    public GamePiece(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public GamePiece(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public void chngColor(int c)
    {
        color = c;
    }
}
