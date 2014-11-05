package camp7506.assignment1.cardgame24;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.Scanner;

import camp7506.assignment1.cardgame24.R;

import static android.content.Context.MODE_WORLD_READABLE;

public class startList extends Activity{

    public final static String MODE_MESSAGE = "camp7506.assignment1.cardgame24.GAME_MODE";
    private Context context;
    TextView prac_round,time_score,challenge_score;
    int roundPractise;
    int roundChallenge;
    int roundTime;
    int highScoreChallenge;
    int highScoreTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start_list);

        context = this;
        readConfig();
        prac_round = (TextView) findViewById(R.id.practiseRound);
        time_score = (TextView) findViewById(R.id.timeScore);
        challenge_score = (TextView) findViewById(R.id.challengeScore);

        prac_round.setText("Practise Played: " + Integer.toString(roundPractise));
        challenge_score.setText("Highest Challenge Score: " + Integer.toString(highScoreChallenge));
        time_score.setText("Highest Time Limited Score: " + Integer.toString(highScoreTime));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_list, menu);
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

    public void onClick_practise(View view) {
        Intent intent = new Intent(this,PracticeModeActivity.class);
        intent.putExtra(MODE_MESSAGE,"practise");
        startActivity(intent);
    }

    public void onClick_challenge(View view){
        Intent intent = new Intent(this,PracticeModeActivity.class);
        intent.putExtra(MODE_MESSAGE,"challenge");
        startActivity(intent);
    }

    public void onClick_time(View view){
        Intent intent = new Intent(this,PracticeModeActivity.class);
        intent.putExtra(MODE_MESSAGE,"time");
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        readConfig();
        prac_round.setText("Practise Played: " + Integer.toString(roundPractise));
        challenge_score.setText("Highest Challenge Score: " + Integer.toString(highScoreChallenge));
        time_score.setText("Highest Time Limited Score: " + Integer.toString(highScoreTime));
        super.onResume();
    }

    public void readConfig(){
        BufferedReader input = null;
        String path=context.getFilesDir().getAbsolutePath()+"/config.cfg";
        File file = new File (path);
        if(file.exists()) {
            try {
                int counter=2;
                input = new BufferedReader(new InputStreamReader(openFileInput("config.cfg")));
                String line;
                Scanner scan = new Scanner(input);
                String[] split;
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
                try {
                    roundPractise = 0;
                    roundChallenge = 0;
                    roundTime = 0;
                    highScoreChallenge = 0;
                    highScoreTime = 0;
                    String separator = System.getProperty("line.separator");
                    FileOutputStream overWrite = context.openFileOutput("config.cfg", Context.MODE_WORLD_READABLE);
                    overWrite.write(("### Config ###"+separator).getBytes());
                    overWrite.write(("round_practise=0"+separator).getBytes());
                    overWrite.write(("high_score_challenge=0"+separator).getBytes());
                    overWrite.write(("round_challenge=0"+separator).getBytes());
                    overWrite.write(("high_score_time=0"+separator).getBytes());
                    overWrite.write(("round_time=0"+separator).getBytes());
                    overWrite.write("### Config ###".getBytes());
                    overWrite.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (Exception e2){
                    e2.printStackTrace();
                }
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
        else{
            BufferedWriter writer = null;
            roundPractise = 0;
            roundChallenge = 0;
            roundTime = 0;
            highScoreChallenge = 0;
            highScoreTime = 0;
            try {
                String separator = System.getProperty("line.separator");
                FileOutputStream openFileOutput = context.openFileOutput("config.cfg", Context.MODE_WORLD_READABLE);
                openFileOutput.write(("### Config ###"+separator).getBytes());
                openFileOutput.write(("round_practise=0"+separator).getBytes());
                openFileOutput.write(("high_score_challenge=0"+separator).getBytes());
                openFileOutput.write(("round_challenge=0"+separator).getBytes());
                openFileOutput.write(("high_score_time=0"+separator).getBytes());
                openFileOutput.write(("round_time=0"+separator).getBytes());
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
        }
    }
}
