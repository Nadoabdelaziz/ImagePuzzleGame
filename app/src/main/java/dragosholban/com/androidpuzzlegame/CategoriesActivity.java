package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

import dragosholban.com.androidpuzzlegame.R;

public class CategoriesActivity extends AppCompatActivity {

    ImageAdapter imgAdpt;
    CustomUnlockedImageAdapter unlocked_img_adapter;
    TextView txt;
    ImageView imgback;
    ImageView Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        Settings = (ImageView) findViewById(R.id.settingsbtn);

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }

                Intent intent =new Intent(CategoriesActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        txt = (TextView) findViewById(R.id.typeCat);
        imgback = (ImageView) findViewById(R.id.imageBtnback);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                Intent intent = new Intent(CategoriesActivity.this, ThirdFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");


        txt.setText(title.toString());

        SharedPreferences preff = getSharedPreferences("Unlocked", Context.MODE_PRIVATE);
        Set<String> new_st = preff.getStringSet("unlocked_images",null);

        Log.d("TAG", "onCreate: "+new_st);
//        Set<String> newCatSet = new HashSet<>();
//        for (String element :new_st) {
//            // Implementing for loop
//            Log.d("Deb", "KOLO : "+ element);
//            if(element.contains("nature")){
//                Log.d("Deb", "here is "+element);
//                newCatSet.add(element);
//            }
//        }
//        Log.d("Deb", "onCreate: "+newCatSet);
        String[] saved_images =new_st.toArray(new String[new_st.size()]) ;
        imgAdpt = new ImageAdapter(this,saved_images,title);

        GridView grid = findViewById(R.id.cats_grid);
        grid.setAdapter(imgAdpt);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
//                    intent.putExtra("assetName", files[i % files.length]);
//                    intent.putExtra("levelname",level);
//                    startActivity(intent);

                //Toast.makeText(MainActivity.this, view.get, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), LevelSelectionActivity.class);
//                    intent.putExtra("assetName", files[i % files.length]);
//                    startActivity(intent);
            }

        });

    }
}
