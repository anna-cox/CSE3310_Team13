package team13.nim;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Created by Addison on 11/29/2017.
 */
//@TargetApi(11)
public class GamePiece extends android.support.v7.widget.AppCompatButton {

    int color; //0=unselected, 1=selected, 2=removed
    int numRow;
    AttributesImpl a = new AttributesImpl();
    int index = a.getIndex("minWidth");

    public GamePiece(Context context)
    {
        super(context);

    }
    public GamePiece(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        numRow = attrs.getAttributeIntValue(index,8);
    }
    public GamePiece(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        numRow = attrs.getAttributeIntValue(index,8);
    }

    public void chngColor(int c)
    {
        color = c;
        if(color == 0)
            this.setBackgroundResource(R.drawable.circle);
        else if(color == 1)
            this.setBackgroundResource(R.drawable.pressed);
        else
            this.setBackgroundResource(R.drawable.removed);
    }

    public int getColor()
    {
        return color;
    }

    public int getRow()
    {
        return numRow;
    }

}
