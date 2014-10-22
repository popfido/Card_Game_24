package camp7506.assignment1.cardgame24;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;


public class PracticeModeActivity extends Activity {

    ImageButton btn_add,btn_minus,btn_multiply,btn_divide,btn_equal,btn_rec,btn_clr,btn_left,btn_right,btn_pos1,btn_pos2,btn_pos3,btn_pos4;
    TextView general_io;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_add = (ImageButton) findViewById(R.id.btn_add);
        btn_minus = (ImageButton) findViewById(R.id.btn_minus);
        btn_multiply = (ImageButton) findViewById(R.id.btn_multiply);
        btn_divide = (ImageButton) findViewById(R.id.btn_divide);
        btn_equal = (ImageButton) findViewById(R.id.btn_equal);
        btn_rec = (ImageButton) findViewById(R.id.btn_rec);
        btn_clr = (ImageButton) findViewById(R.id.btn_clr);
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        btn_right = (ImageButton) findViewById(R.id.btn_right);
        btn_pos1 = (ImageButton) findViewById(R.id.pos1);
        btn_pos2 = (ImageButton) findViewById(R.id.pos2);
        btn_pos3 = (ImageButton) findViewById(R.id.pos3);
        btn_pos4 = (ImageButton) findViewById(R.id.pos4);
        general_io = (TextView) findViewById(R.id.textView);

        setContentView(R.layout.activity_practice_mode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practice_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnClick_clr(View view){
        general_io.setText(" ");
    }
}
