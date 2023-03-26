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







        final Dialog dialog = new Dialog(LevelDoneActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_dialog_alert);

        Button btn = dialog.findViewById(R.id.exitbtn);


//
//        SharedPreferences sharedPref= getSharedPreferences("Points", 0);
//        TextView points = dialog.findViewById(R.id.pointstext);
//        Long number = sharedPref.getLong("rewards", 0);
//        points.setText(String.valueOf(number));

        dialog.show();

        Button dialogButton = (Button) dialog.findViewById(R.id.claim);
        Button dialogButton_3 = (Button) dialog.findViewById(R.id.claim_3);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Points",0);
                Long s1 = sharedPreferences.getLong("rewards",0) + 1000;
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putLong("rewards", s1);
                myEdit.commit();
                dialog.dismiss();
            }
        });
        dialogButton_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)  {
//                Toast.makeText(LevelDoneActivity.this, "ADS", Toast.LENGTH_SHORT).show();
                loadRewardedAd(dialog);
            }
        });
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setMessage("Write your message here.");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();


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


//        SharedPreferences sharedPreferences = getSharedPreferences("Points",0);
//        Long s1 = sharedPreferences.getLong("rewards",0) + 1000;
////        //get points
////        Toast.makeText(this, s1, Toast.LENGTH_SHORT).show();
//
//        // edit points number
//        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//        myEdit.putLong("rewards", s1);
//        myEdit.commit();


        Button button = (Button) findViewById(R.id.nextlvl);
//        Button twice_score = (Button) findViewById(R.id.twice_score1);

        Button back = (Button) findViewById(R.id.Back);
//        TextView score = (TextView) findViewById(R.id.score);


        Intent intent = getIntent();
        String assetName = intent.getStringExtra("assetName");
        String level = intent.getStringExtra("levelname");
//        String finalscore = intent.getStringExtra("score");


//        Toast.makeText(this, finalscore, Toast.LENGTH_SHORT).show();
//        score.setText("1000");




//        Toast.makeText(this, assetName, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, level, Toast.LENGTH_SHORT).show();
//        twice_score.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view)  {
////                Toast.makeText(LevelDoneActivity.this, "ADS", Toast.LENGTH_SHORT).show();
//                loadRewardedAd();
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)  {
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
                Intent intent = new Intent(getApplicationContext(), GameMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadRewardedAd(Dialog D1){
        if (mRewardedAd.isLoaded()) {
            Activity activityContext = LevelDoneActivity.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    D1.dismiss();
                    // Ad closed.
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Toast.makeText(LevelDoneActivity.this, "You Earned Reward", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("Points",0);
                    Long s1 = sharedPreferences.getLong("rewards",0) + 1000;

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