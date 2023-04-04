package dragosholban.com.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView mNotification_on_btn=(ImageView)findViewById(R.id.on_btn);
        ImageView mNotification_off_btn=(ImageView)findViewById(R.id.off_btn);

        mNotification_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotification_on_btn.setVisibility(View.GONE);
                mNotification_off_btn.setVisibility(View.VISIBLE);
                Toast.makeText(SettingsActivity.this, "Sounds OFF", Toast.LENGTH_SHORT).show();
            }
        });
        mNotification_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotification_off_btn.setVisibility(View.GONE);
                mNotification_on_btn.setVisibility(View.VISIBLE);
                Toast.makeText(SettingsActivity.this, "Sounds ON", Toast.LENGTH_SHORT).show();
            }
        });
    }
}