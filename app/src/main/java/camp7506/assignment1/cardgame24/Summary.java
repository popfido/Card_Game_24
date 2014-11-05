package camp7506.assignment1.cardgame24;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Summary extends Activity implements View.OnClickListener{

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_summary);

        context = this;

        ((ImageView)findViewById(R.id.imageView12)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.imageView13)).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        readConfig();
        super.onResume();
    }

    protected void readConfig(){
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
                                ((TextView)findViewById(R.id.textView4)).setText("Played: " + split[1]);
                                break;
                            case 3:
                                ((TextView)findViewById(R.id.textView10)).setText("Challenge: " + split[1]);
                                break;
                            case 4:
                                ((TextView)findViewById(R.id.textView6)).setText("Played: " + split[1]);
                                break;
                            case 5:
                                ((TextView)findViewById(R.id.textView10)).append(" Time: " + split[1]);
                                break;
                            case 6:
                                ((TextView)findViewById(R.id.textView8)).setText("Played: " + split[1]);
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

    @Override
    public void onClick(View view) {
        // Do something in response to button click
        switch (view.getId()) {
            case R.id.imageView12:
            BufferedWriter writer = null;
            try {
                String separator = System.getProperty("line.separator");
                FileOutputStream openFileOutput = context.openFileOutput("config.cfg", Context.MODE_WORLD_READABLE);
                openFileOutput.write(("### Config ###" + separator).getBytes());
                openFileOutput.write(("round_practise=0" + separator).getBytes());
                openFileOutput.write(("high_score_challenge=0" + separator).getBytes());
                openFileOutput.write(("round_challenge=0" + separator).getBytes());
                openFileOutput.write(("high_score_time=0" + separator).getBytes());
                openFileOutput.write(("round_time=0" + separator).getBytes());
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
            readConfig();
            break;
            case R.id.imageView13:
                finish();
                break;
        }
    }
}
