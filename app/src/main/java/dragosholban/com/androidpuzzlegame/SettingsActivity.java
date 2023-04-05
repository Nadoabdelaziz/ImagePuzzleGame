package dragosholban.com.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    ImageView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView mNotification_on_btn=(ImageView)findViewById(R.id.on_btn);
        ImageView mNotification_off_btn=(ImageView)findViewById(R.id.off_btn);

        Button help = (Button) findViewById(R.id.help);
        Button about = (Button) findViewById(R.id.about);
        Button privacy = (Button) findViewById(R.id.privacy);

        btn = (ImageView) findViewById(R.id.baackbtn3);

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

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mpalert = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);

                if(sound) {
                    mpalert.start();
                }

                Intent intent = new Intent(SettingsActivity.this,PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sh = this.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sh.edit();

        Boolean sound = sh.getBoolean("Sounds",true);
        Log.d("TAG", "onCreate: "+sound);


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mpalert = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);

                if(sound) {
                    mpalert.start();
                }

                Intent intent = new Intent(SettingsActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mpalert = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);

                if(sound) {
                    mpalert.start();
                }
                Intent intent = new Intent(SettingsActivity.this,AboutUsActivity.class);
                startActivity(intent);
            }
        });

        if(sound){
            mNotification_off_btn.setVisibility(View.GONE);
            mNotification_on_btn.setVisibility(View.VISIBLE);
        }
        else{
            mNotification_on_btn.setVisibility(View.GONE);
            mNotification_off_btn.setVisibility(View.VISIBLE);
        }

        // mute
        mNotification_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotification_on_btn.setVisibility(View.GONE);
                mNotification_off_btn.setVisibility(View.VISIBLE);
                edit.putBoolean("Sounds",false);
                edit.commit();
                Boolean sound2 = sh.getBoolean("Sounds",false);
                Log.d("TAG", "onClick: new mute"+sound2);
//                startActivity(getIntent());
                Toast.makeText(SettingsActivity.this, "Sounds OFF", Toast.LENGTH_SHORT).show();
            }
        });

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.coinsound);

        // unmute
        mNotification_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                mNotification_off_btn.setVisibility(View.GONE);
                mNotification_on_btn.setVisibility(View.VISIBLE);
                edit.putBoolean("Sounds",true);
                edit.commit();
                Boolean sound2 = sh.getBoolean("Sounds",true);
                Log.d("TAG", "onClick: new unmute"+sound2);

//                startActivity(getIntent());
                Toast.makeText(SettingsActivity.this, "Sounds ON", Toast.LENGTH_SHORT).show();
            }
        });
    }
}