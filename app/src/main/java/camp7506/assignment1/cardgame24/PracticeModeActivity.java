package camp7506.assignment1.cardgame24;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;

import java.util.ArrayList;
import java.util.Objects;

import camp7506.assignment1.cardgame24.formularElem;


public class PracticeModeActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    ImageButton btn_add,btn_minus,btn_multiply,btn_divide,btn_equal,btn_rec,btn_clr,btn_left,btn_right;
    TextView general_io;
    ArrayList<formularElem> formula = new ArrayList<formularElem>();
    int[] srcId;
    card[] card_desk;
    int mode;
    status clickAble = new status();
    ArrayList<status> statusArray = new ArrayList<status>();
    int bracketCounter = 0;

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
        btn_equal.setOnClickListener(this);

        btn_add.setOnTouchListener(this);
        btn_minus.setOnTouchListener(this);
        btn_multiply.setOnTouchListener(this);
        btn_divide.setOnTouchListener(this);
        btn_clr.setOnTouchListener(this);
        btn_rec.setOnTouchListener(this);
        btn_left.setOnTouchListener(this);
        btn_right.setOnTouchListener(this);
        btn_equal.setOnTouchListener(this);

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
        switch (v.getId()) {
            case R.id.btn_add:
                if(clickAble.operate && mode == 1) {
                    general_io.append("+");
                    formularElem temp = new formularElem("+", 1);
                    formula.add(temp);
                    setOnClick(temp);
                }
                break;
            case R.id.btn_minus:
                if(clickAble.operate && mode == 1) {
                    general_io.append("-");
                    formularElem temp = new formularElem("-",1);
                    formula.add(temp);
                    setOnClick(temp);
                }
                break;
            case R.id.btn_multiply:
                if(clickAble.operate && mode == 1) {
                    general_io.append("*");
                    formularElem temp = new formularElem("*",1);
                    formula.add(temp);
                    setOnClick(temp);
                }
                break;
            case R.id.btn_divide:
                if(clickAble.operate && mode == 1) {
                    general_io.append("/");
                    formularElem temp = new formularElem("/",1);
                    formula.add(temp);
                    setOnClick(temp);
                }
                break;
            case R.id.btn_left:
                if(clickAble.left && mode == 1) {
                    general_io.append("(");
                    formularElem temp = new formularElem("(",2);
                    formula.add(temp);
                    setOnClick(temp);
                }
                break;
            case R.id.btn_right:
                if(clickAble.right && bracketCounter > 0 && mode == 1) {
                    general_io.append(")");
                    formularElem temp = new formularElem(")",3);
                    formula.add(temp);
                    setOnClick(temp);
                }
                break;
            case R.id.btn_clr:
                if(mode == 1) {
                    formula.clear();
                    general_io.setText(" ");
                    clickAble = new status();
                    statusArray.clear();
                    for (int i = 0; i < 4; i++)
                        card_desk[i].clicked = false;
                }
            break;
            case R.id.btn_rec:
                if (!formula.isEmpty() && mode == 1){
                    if(formula.get(formula.size()-1).type == 0){
                        card temp = (card)formula.get(formula.size()-1);
                        temp.clicked = false;
                    }
                    String old = general_io.getText().toString();
                    general_io.setText(old.substring(0,old.length()-formula.get(formula.size()-1).content.length()));
                    formula.remove(formula.size() - 1);
                    clickAble = statusArray.get(statusArray.size()-1);
                    statusArray.remove(statusArray.size()-1);
                }
            break;
            case R.id.btn_equal:
                if (clickAble.equal && bracketCounter == 0 && mode == 1) {
                    Jep jep = new Jep();
                    try {
                        jep.parse(general_io.getText().toString());
                        String res = jep.evaluate().toString();
                        general_io.setText(" " + res);
                    } catch (JepException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                    mode = 2;
                }
            break;
            default:
                if (mode == 0)
                    beginGame();
                else {
                    if (clickAble.number && mode == 1)
                        switch (v.getId()) {
                            case R.id.pos1:
                                if (!card_desk[0].clicked) {
                                    formula.add(card_desk[0]);
                                    general_io.append(card_desk[0].content);
                                    card_desk[0].clicked = true;
                                    setOnClick();
                                }
                                break;
                            case R.id.pos2:
                                if (!card_desk[1].clicked) {
                                    formula.add(card_desk[1]);
                                    general_io.append(card_desk[1].content);
                                    card_desk[1].clicked = true;
                                    setOnClick();
                                }
                                break;
                            case R.id.pos3:
                                if (!card_desk[2].clicked) {
                                    formula.add(card_desk[2]);
                                    general_io.append(card_desk[2].content);
                                    card_desk[2].clicked = true;
                                    setOnClick();
                                }
                                break;
                            case R.id.pos4:
                                if (!card_desk[3].clicked) {
                                    formula.add(card_desk[3]);
                                    general_io.append(card_desk[3].content);
                                    card_desk[3].clicked = true;
                                    setOnClick();
                                }
                                break;
                        }
                }
            break;
        }
    }

    public void reset(){
        formula.clear();
        general_io.setText(" ");
        statusArray.clear();
        clickAble = new status();
        mode = 0;
    }

    public void beginGame(){
        for(int i = 0;i<4;i++){
            card_desk[i].reset();
            srcId[i] = getResources().getIdentifier("c"+card_desk[i].src_id,"drawable", getPackageName());
            card_desk[i].object.setImageResource(srcId[i]);
        }
        mode = 1;
    }

    public void setOnClick(formularElem elem){
        statusArray.add(clickAble);
        switch (elem.type) {
            case 0:
                clickAble = new status(false, true, false, true, true);
            break;
            case 1:
                clickAble = new status(true, false, true, false, false);
            break;
            case 2:
                clickAble = new status(true, false, true, false, false);
                bracketCounter++;
            break;
            case 3:
                clickAble = new status(false, true, false, true, true);
                bracketCounter--;
            break;
        }
    }

    public void  setOnClick(){
        statusArray.add(clickAble);
        clickAble = new status(false, true, false, true, true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.btn_add:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_add_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_add));
                break;
            case R.id.btn_minus:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_minus_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_minus));
                break;
            case R.id.btn_multiply:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_multiply_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_multiply));
                break;
            case R.id.btn_divide:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_divide_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_divide));
                break;
            case R.id.btn_equal:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_equal_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_equal));
                break;
            case R.id.btn_rec:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_rec_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_rec));
                break;
            case R.id.btn_clr:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_clr_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_clr));
                break;
            case R.id.btn_left:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_left_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_left));
                break;
            case R.id.btn_right:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_right_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_right));
                break;
        }
        return false;
    }
}
