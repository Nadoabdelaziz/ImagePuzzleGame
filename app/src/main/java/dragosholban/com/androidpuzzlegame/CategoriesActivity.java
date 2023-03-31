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
        // add 3 images to be unlocked by defualt
        AssetManager am = getAssets();
        final String[] files; // get all images
        try {
            files = am.list("img");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Set<String> def_unclocked = new HashSet<String>();
        def_unclocked.add(files[0]);
        def_unclocked.add(files[1]);
        def_unclocked.add(files[2]);


        SharedPreferences preff = getSharedPreferences("Unlocked", Context.MODE_PRIVATE);
        Set<String> new_st = preff.getStringSet("unlocked_images",null);

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
        if( new_st != null){ //there are saved data
            String[] saved_images =new_st.toArray(new String[new_st.size()]) ;
            unlocked_img_adapter = new CustomUnlockedImageAdapter(this,saved_images);
            imgAdpt = new ImageAdapter(this,saved_images);
        }
        else { //save 3 defualt images
            SharedPreferences.Editor  editorr= preff.edit();
            editorr.putStringSet("unlocked_images",def_unclocked);
            editorr.commit();
            String [] def_images = def_unclocked.toArray(new String[def_unclocked.size()]) ;
            unlocked_img_adapter = new CustomUnlockedImageAdapter(this,def_images);
            imgAdpt = new ImageAdapter(this,def_images);
        }

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
