package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;


public class StartGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        Button btn = (Button) findViewById(R.id.start);

//        GetPhotos gt = new GetPhotos(getApplicationContext());
//        gt.getphotos();

//        SharedPreferences prefs = getApplicationContext().getSharedPreferences("px_photos", 0);
//        int size = prefs.getInt("newphotos" + "_size", 0);
//        String array[] = new String[size];
//        for(int i=0;i<size;i++)
//            array[i] = prefs.getString("newphotos" + "_" + i, null);
//
//
//            Log.d("GET", "onCreate: "+ Arrays.toString(array));

        SharedPreferences sh = this.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
//        myEdit.putBoolean("Sounds",true);


        final MediaPlayer mp = MediaPlayer.create(this, R.raw.coinsound);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TheFragmnetsActivity.class);
                startActivity(intent);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }

            }
        });
    }
}