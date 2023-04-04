package dragosholban.com.androidpuzzlegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.Set;

public class LevelDoneActivity extends AppCompatActivity {
    private RewardedAd mRewardedAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_done);



        Intent intent = getIntent();
        String assetName = intent.getStringExtra("assetName");
        String level = intent.getStringExtra("levelname");
        String rewardpts = intent.getStringExtra("rewardpts");


        final Dialog dialog = new Dialog(LevelDoneActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_dialog_alert);

        Button btn = dialog.findViewById(R.id.exitbtn);


        dialog.show();

        Button dialogButton = (Button) dialog.findViewById(R.id.claim);
        Button dialogButton_3 = (Button) dialog.findViewById(R.id.claim_3);



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
                dialog.dismiss();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                SharedPreferences sharedPreferences = getSharedPreferences("Points",0);
                Long s1 = sharedPreferences.getLong("rewards",0) + Long.parseLong(rewardpts);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putLong("rewards", s1);
                myEdit.commit();
                dialog.dismiss();
            }
        });
        dialogButton_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)  {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
//                Toast.makeText(LevelDoneActivity.this, "ADS", Toast.LENGTH_SHORT).show();
                loadRewardedAd(dialog);
            }
        });



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


        Button button = (Button) findViewById(R.id.nextlvl);
//        Button twice_score = (Button) findViewById(R.id.twice_score1);

        Button back = (Button) findViewById(R.id.Back);
//        TextView score = (TextView) findViewById(R.id.score);

        Button restartBtn = (Button) findViewById(R.id.restart);


        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                Intent intent2 = new Intent(getApplicationContext(), PuzzleActivity.class);
                intent2.putExtra("levelname", level);
                intent2.putExtra("assetname", assetName);
                startActivity(intent2);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)  {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                int lvl = Integer.parseInt(level);
                lvl = lvl +1;
//                Toast.makeText(LevelDoneActivity.this, level, Toast.LENGTH_SHORT).show();
                String newlevel= Integer.toString(lvl);

//                Toast.makeText(LevelDoneActivity.this, newlevel, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                intent.putExtra("assetname", assetName);
                intent.putExtra("levelname",newlevel);
                startActivity(intent);
            }
        });

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
                Toast.makeText(LevelDoneActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TheFragmnetsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadRewardedAd(Dialog D1){
        Intent intent = getIntent();
        String rewardpts = intent.getStringExtra("rewardpts");

        if (mRewardedAd.isLoaded()) {
            Activity activityContext = LevelDoneActivity.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    SharedPreferences sh  = activityContext.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sh.edit();
                    final MediaPlayer mp = MediaPlayer.create(activityContext, R.raw.coinsound);
                    Boolean sound = sh.getBoolean("Sounds",true);
                    if(sound) {
                        mp.start();
                    }
                    D1.dismiss();
                    // Ad closed.
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    SharedPreferences sh = activityContext.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                    final MediaPlayer mp = MediaPlayer.create(activityContext, R.raw.coin);
                    Boolean sound = sh.getBoolean("Sounds",true);
                    if(sound) {
                        mp.start();
                    }
                    Toast.makeText(LevelDoneActivity.this, "You Earned Reward", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("Points",0);
                    Long s1 = sharedPreferences.getLong("rewards",0) + 3*(Long.parseLong(rewardpts));

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putLong("rewards", s1);
                    myEdit.commit();
//
//
//                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(getIntent());
//                    D1.dismiss();
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