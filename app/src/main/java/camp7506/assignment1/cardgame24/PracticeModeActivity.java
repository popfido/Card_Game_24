package camp7506.assignment1.cardgame24;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.singularsys.jep.Jep;

import java.util.ArrayList;

import camp7506.assignment1.cardgame24.formularElem;


public class PracticeModeActivity extends Activity implements View.OnClickListener{

    ImageButton btn_add,btn_minus,btn_multiply,btn_divide,btn_equal,btn_rec,btn_clr,btn_left,btn_right,btn_pos1,btn_pos2,btn_pos3,btn_pos4;
    TextView general_io;
    ArrayList<formularElem> formula;
    int[] srcId;
    card[] card_desk;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_practice_mode);

        btn_add = (ImageButton) findViewById(R.id.btn_add);
        btn_minus = (ImageButton) findViewById(R.id.btn_minus);
        btn_multiply = (ImageButton) findViewById(R.id.btn_multiply);
        btn_divide = (ImageButton) findViewById(R.id.btn_divide);
        btn_equal = (ImageButton) findViewById(R.id.btn_equal);
        btn_rec = (ImageButton) findViewById(R.id.btn_rec);
        btn_clr = (ImageButton) findViewById(R.id.btn_clr);
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        btn_right = (ImageButton) findViewById(R.id.btn_right);
        general_io = (TextView) findViewById(R.id.textView);

        srcId = new int[4];
        card_desk = new card[] {new card((ImageButton) findViewById(R.id.pos1)),
                                new card((ImageButton) findViewById(R.id.pos2)),
                                new card((ImageButton) findViewById(R.id.pos3)),
                                new card((ImageButton) findViewById(R.id.pos4))};

        btn_add.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_clr.setOnClickListener(this);
        btn_rec.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        for(int i=0;i<4;i++)
            card_desk[i].object.setOnClickListener(this);

        mode = 0;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                general_io.append("+");
            break;
            case R.id.btn_minus:
                general_io.append("-");
            break;
            case R.id.btn_multiply:
                general_io.append("*");
            break;
            case R.id.btn_divide:
                general_io.append("/");
            break;
            case R.id.btn_left:
                general_io.append("(");
            break;
            case R.id.btn_right:
                general_io.append(")");
            case R.id.btn_clr:
                general_io.setText(" ");
            break;
            case R.id.pos1:
                if (mode == 0)
                    beginGame();
            break;
        }
    }

    public void reset(){

    }

    public void beginGame(){
        for(int i = 0;i<4;i++){
            card_desk[i].reset();
            srcId[i] = getResources().getIdentifier("c"+card_desk[i].src_id,"drawable", getPackageName());
            card_desk[i].object.setImageResource(srcId[i]);
        }
        mode = 1;
    }
}
