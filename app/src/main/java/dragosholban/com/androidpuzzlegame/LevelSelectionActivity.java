package dragosholban.com.androidpuzzlegame;

import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LevelSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

        Intent intent = getIntent();
        String mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath");
        String mCurrentPhotoUri = intent.getStringExtra("mCurrentPhotoUri");

            Bundle zizo = getIntent().getExtras();
            String assetname = zizo.getString("assetName");


//        Toast.makeText(this, assetname, Toast.LENGTH_SHORT).show();

        Button btn1 = (Button) findViewById(R.id.easy);
        Button btn2 = (Button) findViewById(R.id.hard);
        Button btn3 = (Button) findViewById(R.id.expert);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                if(mCurrentPhotoPath!=null){
                    intent.putExtra("levelname", btn1.getText());
                    intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
                    startActivity(intent);
                }
                else if(mCurrentPhotoUri !=null){
                    intent.putExtra("levelname", btn1.getText());
                    intent.putExtra("mCurrentPhotoUri", mCurrentPhotoUri);
                    startActivity(intent);
                }
                else{
                    intent.putExtra("levelname", btn1.getText());
                    intent.putExtra("assetname", assetname);
                    startActivity(intent);
                }


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                if(mCurrentPhotoPath!=null){
                    intent.putExtra("levelname", btn2.getText());
                    intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
                    startActivity(intent);
                }
                else if(mCurrentPhotoUri !=null){
                    intent.putExtra("levelname", btn2.getText());
                    intent.putExtra("mCurrentPhotoUri", mCurrentPhotoUri);
                    startActivity(intent);
                }
                else{
                    intent.putExtra("levelname", btn2.getText());
                    intent.putExtra("assetname", assetname);
                    startActivity(intent);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                if(mCurrentPhotoPath!=null){
                    intent.putExtra("levelname", btn3.getText());
                    intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
                    startActivity(intent);
                }
                else if(mCurrentPhotoUri !=null){
                    intent.putExtra("levelname", btn3.getText());
                    intent.putExtra("mCurrentPhotoUri", mCurrentPhotoUri);
                    startActivity(intent);
                }
                else{
                    intent.putExtra("levelname", btn3.getText());
                    intent.putExtra("assetname", assetname);
                    startActivity(intent);
                }
            }
        });
    }
}