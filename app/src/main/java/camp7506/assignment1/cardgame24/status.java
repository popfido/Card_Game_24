package camp7506.assignment1.cardgame24;

import android.widget.ImageButton;

/**
 * Created by Fido on 24/10/14.
 */
public class status {
    boolean number,operate, left, right, equal;

    status(){
        number = true;
        operate = false;
        left = true;
        right = false;
        equal = false;
    }

    status(boolean n, boolean o, boolean l, boolean r, boolean e){
        number = n;
        operate = o;
        left = l;
        right = r;
        equal = e;
    }
}
