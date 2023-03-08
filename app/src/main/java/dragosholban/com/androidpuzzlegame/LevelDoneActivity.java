package dragosholban.com.androidpuzzlegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LevelDoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_done);

        Button button = (Button) findViewById(R.id.nextlvl);
        Button back = (Button) findViewById(R.id.Back);


        Intent intent = getIntent();
        String assetName = intent.getStringExtra("assetName");
        String level = intent.getStringExtra("level");


        Toast.makeText(this, assetName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, level, Toast.LENGTH_SHORT).show();

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
                intent.putExtra("assetname", assetName);
                intent.putExtra("levelname",newlevel);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}