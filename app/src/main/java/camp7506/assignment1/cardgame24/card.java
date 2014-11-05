package camp7506.assignment1.cardgame24;

import android.widget.ImageButton;
import java.util.Random;

/**
 * Created by Fido on 23/10/14.
 */
public class card extends formularElem{
    public ImageButton object;
    public String src_id;
    public boolean clicked;

    card(ImageButton btn){
        super("",0);
        object = btn;
    }

    public void resetCards(int n){
        num = n;
        content = Integer.toString(num);
        clicked = false;
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
