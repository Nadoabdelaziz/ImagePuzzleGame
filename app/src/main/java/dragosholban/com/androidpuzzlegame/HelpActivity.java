package dragosholban.com.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dragosholban.com.androidpuzzlegame.R;

public class HelpActivity extends AppCompatActivity {
    TextView tv;
    ImageView btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        btn = (ImageView) findViewById(R.id.baackbtn5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mpalert = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);

                if(sound) {
                    mpalert.start();
                }
                onBackPressed();
            }
        });

        tv = (TextView) findViewById(R.id.textView16);
        tv .setMovementMethod(new ScrollingMovementMethod());
    }
}