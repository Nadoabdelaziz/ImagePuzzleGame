package dragosholban.com.androidpuzzlegame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class losegameactivity extends AppCompatActivity {

    private RewardedAd mRewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losegameactivity);


        mRewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
            }
        };
        mRewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);



        final Dialog dialog2 = new Dialog(losegameactivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.activity_lose_dialog_alert);

        Button btn = dialog2.findViewById(R.id.exitbtn2);
        Button contBTN = dialog2.findViewById(R.id.continues);
        Button Restart = dialog2.findViewById(R.id.restart);

        Button back = dialog2.findViewById(R.id.backmenu);
        TextView score = (TextView) findViewById(R.id.score);

        Button btnBack = (Button) findViewById(R.id.Back);
        Button btrnrestart = (Button) findViewById(R.id.restartBtn);


        dialog2.show();

        Intent intent = getIntent();
//        String finalscore = intent.getStringExtra("score");
//


        contBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                loadRewardedAd(dialog2);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                dialog2.dismiss();
            }
        });

        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                String assetname = intent.getStringExtra("assetName");
                String level = intent.getStringExtra("levelname");
                Intent intent2 = new Intent(getApplicationContext(), PuzzleActivity.class);
                intent2.putExtra("levelname", level);
                intent2.putExtra("assetname", assetname);
                startActivity(intent2);
            }
        });

        btrnrestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                String assetname = intent.getStringExtra("assetName");
                String level = intent.getStringExtra("levelname");
                Intent intent2 = new Intent(getApplicationContext(), PuzzleActivity.class);
                intent2.putExtra("levelname", level);
                intent2.putExtra("assetname", assetname);
                startActivity(intent2);
            }
        });





//        score.setText(finalscore.toString());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                Intent intent = new Intent(getApplicationContext(), TheFragmnetsActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }

                Intent intent = new Intent(getApplicationContext(), TheFragmnetsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadRewardedAd(Dialog D1){
        if (mRewardedAd.isLoaded()) {
            Activity activityContext = losegameactivity.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    D1.dismiss();
                    Intent intent = getIntent();
                    String assetname = intent.getStringExtra("assetName");
                    String level = intent.getStringExtra("levelname");
                    Intent intent2 = new Intent(getApplicationContext(), PuzzleActivity.class);
                    intent2.putExtra("levelname", level);
                    intent2.putExtra("assetname", assetname);
                    startActivity(intent2);
                    // Ad closed.
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Toast.makeText(losegameactivity.this, "You Earned Reward", Toast.LENGTH_SHORT).show();
                    D1.dismiss();

                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    // Ad failed to display.
                }
            };
            mRewardedAd.show(activityContext, adCallback);
        } else {
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }




    }

}