package dragosholban.com.androidpuzzlegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelDoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_done);

        Button button = (Button) findViewById(R.id.nextlvl);

        Intent intent = getIntent();
        final String assetName = intent.getStringExtra("assetName");
        final String level = intent.getStringExtra("levelname");


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)  {

                String newlevel="";

                if(level == "Easy"){
                    newlevel = "Hard";
                }
                else if(level == "Hard"){
                    newlevel = "Expert";
                }

                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                intent.putExtra("assetName", assetName);
                intent.putExtra("levelname",newlevel);
                startActivity(intent);
            }
        });
    }
}