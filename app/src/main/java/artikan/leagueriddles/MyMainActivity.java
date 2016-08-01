package artikan.leagueriddles;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class MyMainActivity extends Activity {

    public String answer = "NONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        adjustFont();

        // when clicked on next, go to nextriddle
        ImageView next = (ImageView) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextRiddle();
            }
        });


        // empty textview onclick
        final EditText e = (EditText) findViewById(R.id.editText);

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.setText("");
            }
        });

        // guess
        ImageView guess = (ImageView) findViewById(R.id.guess);
        guess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                if (e.getText().toString().equals(answer))
                {
                    Toast t_ = Toast.makeText(getApplicationContext(), "Congratulations! " + answer + " is correct answer.", Toast.LENGTH_LONG);
                    t_.setGravity(Gravity.TOP, 0, 300);
                    t_.show();
                    e.setText("Enter your answer here");
                    nextRiddle();
                }
                else
                {
                    Toast t_ = Toast.makeText(getApplicationContext(), "Incorrect. Try again.", Toast.LENGTH_LONG);
                    t_.setGravity(Gravity.TOP, 0, 300);
                    t_.show();
                }
            }
        });

        // show toast
        Toast t_ = Toast.makeText(getApplicationContext(), "Good Luck!", Toast.LENGTH_LONG);
        t_.setGravity(Gravity.TOP, 0, 300);
        t_.show();

        // finally, load first riddle
        nextRiddle();


    }

    private void adjustFont() {
        TextView txt = (TextView) findViewById(R.id.textView);
        Typeface font = Typeface.createFromAsset(getAssets(), "Vivaldi.ttf");
        txt.setTypeface(font);
    }

    private void nextRiddle() {
        TextView txt = (TextView) findViewById(R.id.textView);

        try
        {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray riddles = obj.getJSONArray("riddles");
            Random rnd = new Random();
            int i = rnd.nextInt(riddles.length());
            JSONObject riddle = riddles.getJSONObject(i);

            txt.setText(riddle.getString("riddle"));
            answer = riddle.getString("answer");
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
