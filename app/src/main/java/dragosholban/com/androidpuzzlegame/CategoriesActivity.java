package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CategoriesActivity extends AppCompatActivity {

    ImageAdapter imgAdpt;
    CustomUnlockedImageAdapter unlocked_img_adapter;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        txt = (TextView) findViewById(R.id.typeCat);

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
