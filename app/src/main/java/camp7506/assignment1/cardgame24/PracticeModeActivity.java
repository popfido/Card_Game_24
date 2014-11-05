package camp7506.assignment1.cardgame24;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

import camp7506.assignment1.cardgame24.formularElem;


public class PracticeModeActivity extends Activity implements View.OnClickListener, View.OnTouchListener{

    ImageButton btn_add,btn_minus,btn_multiply,btn_divide,btn_equal,btn_rec,btn_clr,btn_left,btn_right;
    TextView general_io, mTextField;
    ImageView resImg;
    ArrayList<formularElem> formula = new ArrayList<formularElem>();
    int[] srcId;
    card[] card_desk;
    int mode;
    status clickAble = new status();
    ArrayList<status> statusArray = new ArrayList<status>();
    int bracketCounter = 0;


    InputStream gameRandomer;

    int winCounter = 0;
    String Game_Mode;
    boolean gameOver = false;
    int roundPractise = 0;
    int roundChallenge = 0;
    int roundTime = 0;
    int highScoreChallenge = 0;
    int highScoreTime = 0;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_practice_mode);

        Intent intent = getIntent();
        Game_Mode = intent.getStringExtra(startList.MODE_MESSAGE);

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
        resImg = (ImageView) findViewById(R.id.imageView2);

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
        ((ImageView)findViewById(R.id.imageView2)).setOnClickListener(this);

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

        /**
         * Save File reader.
         */
        context = this;
        readConfig();

        changeMode();
        mTextField = (TextView) findViewById(R.id.mTextField);


        if(Game_Mode.equals("time"))
        new CountDownTimer(300000, 1000) {//CountDownTimer(edittext1.getText()+edittext2.getText()) also parse it to long

            public void onTick(long millisUntilFinished) {
                mTextField.setText("Seconds Remaining: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                mTextField.setText("Time Over");
                gameOver = true;
                mode = 2;
            }
        }.start();

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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView2:
                if(mode == 2 && !gameOver)
                    reset();
                else
                    finish();
                break;
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
                    for (int i = 0; i < 4; i++) {
                        card_desk[i].clicked = false;
                        card_desk[i].object.setAlpha((float) 1.0);
                    }
                }
            break;
            case R.id.btn_rec:
                if (!formula.isEmpty() && mode == 1){
                    if(formula.get(formula.size()-1).type == 0){
                        card temp = (card)formula.get(formula.size()-1);
                        temp.clicked = false;
                        temp.object.setAlpha((float) 1.0);
                    }
                    String old = general_io.getText().toString();
                    general_io.setText(old.substring(0,old.length()-formula.get(formula.size()-1).content.length()));
                    formula.remove(formula.size() - 1);
                    clickAble = statusArray.get(statusArray.size()-1);
                    statusArray.remove(statusArray.size()-1);
                }
            break;
            case R.id.btn_equal:
                if (clickAble.equal && bracketCounter == 0 && mode == 1 && card_desk[0].clicked &&
                        card_desk[1].clicked && card_desk[2].clicked && card_desk[3].clicked) {
                    Jep jep = new Jep();
                    try {
                        jep.parse(general_io.getText().toString());
                        String res = jep.evaluate().toString();
                        if(res.equals("24.0")) {
                            general_io.setText(" " + res);
                            resImg.setImageResource(getResources().getIdentifier("icon_tick", "drawable", getPackageName()));
                            winCounter++;
                            ((TextView)findViewById(R.id.textView2)).setText("Win: "+Integer.toString(winCounter));
                            modeJudger(true);
                        }
                        else {
                            general_io.setText(" " + res);
                            resImg.setImageResource(getResources().getIdentifier("icon_wrong","drawable",getPackageName()));
                            modeJudger(false);
                            if(!Game_Mode.equals("practise"))
                                gameOver = true;
                        }
                    } catch (JepException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                        general_io.setText("Error: divide 0!");
                        resImg.setImageResource(getResources().getIdentifier("icon_wrong","drawable",getPackageName()));
                        modeJudger(false);
                        if(!Game_Mode.equals("practise"))
                            gameOver = true;
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
                                    card_desk[0].object.setAlpha((float) 0.4);
                                    setOnClick();
                                }
                                break;
                            case R.id.pos2:
                                if (!card_desk[1].clicked) {
                                    formula.add(card_desk[1]);
                                    general_io.append(card_desk[1].content);
                                    card_desk[1].clicked = true;
                                    card_desk[1].object.setAlpha((float) 0.4);
                                    setOnClick();
                                }
                                break;
                            case R.id.pos3:
                                if (!card_desk[2].clicked) {
                                    formula.add(card_desk[2]);
                                    general_io.append(card_desk[2].content);
                                    card_desk[2].clicked = true;
                                    card_desk[2].object.setAlpha((float) 0.4);
                                    setOnClick();
                                }
                                break;
                            case R.id.pos4:
                                if (!card_desk[3].clicked) {
                                    formula.add(card_desk[3]);
                                    general_io.append(card_desk[3].content);
                                    card_desk[3].clicked = true;
                                    card_desk[3].object.setAlpha((float) 0.4);
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
        for (int i = 0; i < 4; i++) {
            card_desk[i].object.setImageResource(getResources().getIdentifier("pre","drawable", getPackageName()));
            card_desk[i].clicked = false;
            card_desk[i].object.setAlpha((float) 1.0);
        }
        resImg.setImageResource(getResources().getIdentifier("mh","drawable",getPackageName()));
        mode = 0;
    }

    public void beginGame(){
        gameRandomer = getResources().openRawResource(R.raw.all_ans);
        InputStreamReader inputReader = new InputStreamReader(gameRandomer);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;
        Scanner scan = new Scanner(bufferedReader);
        String[] split;
        int lineCounter = 0;
        int targetInt = card.randInt(0,1361);
        try {
            while(((line = bufferedReader.readLine()) != null) && (lineCounter != targetInt)) {
                lineCounter++;
            }
            split = scan.nextLine().split(",");
            List<String> list = new ArrayList<String>(Arrays.asList(split));
            for(int i = 0;i<4;i++){
                targetInt = card.randInt(0,3-i);
                card_desk[i].src_id = Integer.toString(Integer.parseInt(list.get(targetInt)) + 13 * card.randInt(0,3));
                card_desk[i].resetCards(Integer.parseInt(list.get(targetInt)));
                list.remove(targetInt);
                srcId[i] = getResources().getIdentifier("c"+card_desk[i].src_id,"drawable", getPackageName());
                card_desk[i].object.setImageResource(srcId[i]);
            }
            mode = 1;
            gameRandomer.close();
        } catch (IOException e) {
        }
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

    public void setOnClick(){
        statusArray.add(clickAble);
        clickAble = new status(false, true, false, true, true);
    }

    public void modeJudger(boolean right){
        if(Game_Mode.equals("practise"))
            roundPractise++;
        else if(Game_Mode.equals("challenge")) {
            highScoreChallenge = (highScoreChallenge > winCounter) ? highScoreChallenge : winCounter;
            roundChallenge++;
        }
        else if(Game_Mode.equals("time") && false){
            highScoreTime = (highScoreTime>winCounter)?highScoreTime:winCounter;
            roundTime++;
        }
        return;
    }

    public void readConfig(){
        BufferedReader input = null;
        String path=context.getFilesDir().getAbsolutePath()+"/config.cfg";
        File file = new File ( path );
        if(file.exists()) {
            try {
                int counter=2;
                input = new BufferedReader(new InputStreamReader(openFileInput("config.cfg")));
                String line;
                Scanner scan = new Scanner(input);
                String[] split;
                StringBuffer buffer = new StringBuffer();
                if((line = input.readLine()) != null)
                while (counter != 7) {
                    split = scan.nextLine().split("=");
                    switch (counter){
                        case 2:
                            roundPractise = Integer.parseInt(split[1]);
                            break;
                        case 3:
                            highScoreChallenge = Integer.parseInt(split[1]);
                            break;
                        case 4:
                            roundChallenge = Integer.parseInt(split[1]);
                            break;
                        case 5:
                            highScoreTime = Integer.parseInt(split[1]);
                            break;
                        case 6:
                            roundTime= Integer.parseInt(split[1]);
                            break;
                    }
                    counter++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void changeMode(){
        if(Game_Mode.equals("challenge"))
            ((ImageView) findViewById(R.id.imageView)).setImageDrawable(getResources().getDrawable(R.drawable.icon_challenge));
        if(Game_Mode.equals("time")){
            ((ImageView) findViewById(R.id.imageView)).setImageDrawable(getResources().getDrawable(R.drawable.icon_time_lim));

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mode == 1)
        switch (v.getId()) {
            case R.id.btn_add:
                if(clickAble.operate) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_add_c));
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_add));
                }
                break;
            case R.id.btn_minus:
                if(clickAble.operate) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_minus_c));
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_minus));
                }
                break;
            case R.id.btn_multiply:
                if(clickAble.operate) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_multiply_c));
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_multiply));
                }
                break;
            case R.id.btn_divide:
                if (clickAble.operate) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_divide_c));
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_divide));
                }
                break;
            case R.id.btn_equal:
                if(clickAble.equal) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_equal_c));
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_equal));
                }
                break;
            case R.id.btn_rec:
                if(!formula.isEmpty()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_rec_c));
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_rec));
                }
                break;
            case R.id.btn_clr:
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_clr_c));
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.btn_clr));
                break;
            case R.id.btn_left:
                if(clickAble.left) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_left_c));
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_left));
                }
                break;
            case R.id.btn_right:
                if(clickAble.right) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_right_c));
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                        ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.btn_right));
                }
                break;
        }
        return false;
    }

    @Override
    protected void onPause(){
        BufferedWriter writer = null;
        try {
            String separator = System.getProperty("line.separator");
            FileOutputStream openFileOutput = context.openFileOutput("config.cfg", Context.MODE_WORLD_READABLE);
            openFileOutput.write(("### Config ###"+separator).getBytes());
            openFileOutput.write(("round_practise="+Integer.toString(roundPractise)+separator).getBytes());
            openFileOutput.write(("high_score_challenge="+Integer.toString(highScoreChallenge)+separator).getBytes());
            openFileOutput.write(("round_challenge=0"+Integer.toString(roundChallenge)+separator).getBytes());
            openFileOutput.write(("high_score_time=0"+Integer.toString(highScoreTime)+separator).getBytes());
            openFileOutput.write(("round_time=0"+Integer.toString(roundTime)+separator).getBytes());
            openFileOutput.write("### Config ###".getBytes());
            openFileOutput.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        super.onPause();
    }
}
