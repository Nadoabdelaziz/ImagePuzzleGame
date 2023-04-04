package dragosholban.com.androidpuzzlegame;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class FirstFragment extends Fragment {


    CustomUnlockedImageAdapter unlocked_img_adapter;
    ImageAdapter imgAdpt;
    Context context;
    RecyclerView recyclerView;
    String mCurrentPhotoPath;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3;
    static final int REQUEST_IMAGE_GALLERY = 4;

    //adbom ads
    private AdView madView;
    public static final String REWARD_AD_UNIT_ID = "ca-app-pub-2145987647160470/2803949040";
    private RewardedAd mRewardedAd;

    private ImageButton mButton;
    private ImageView Settings;
    protected View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        this.mView = view;

        Settings = (ImageView) view.findViewById(R.id.settingsimg);

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });

        mRewardedAd = new RewardedAd(getContext(),
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

        mButton = view.findViewById(R.id.adwatch2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                loadRewardedAd();
            }
        });


        // add 3 images to be unlocked by defualt
        AssetManager am = getContext().getAssets();
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
        //    Toast.makeText(MainActivity.this, Integer.toString(st.size()), Toast.LENGTH_SHORT).show();

        SharedPreferences preff = getContext().getSharedPreferences("Unlocked",Context.MODE_PRIVATE);
        Set<String> new_st = preff.getStringSet("unlocked_images",null);
        if( new_st != null){ //there are saved data
            String[] saved_images =new_st.toArray(new String[new_st.size()]) ;
            unlocked_img_adapter = new CustomUnlockedImageAdapter(getContext(),saved_images);
            imgAdpt = new ImageAdapter(getContext(),saved_images);
        }
        else { //save 3 defualt images
            SharedPreferences.Editor  editorr= preff.edit();
            editorr.putStringSet("unlocked_images",def_unclocked);
            editorr.commit();
            String [] def_images = def_unclocked.toArray(new String[def_unclocked.size()]) ;
            unlocked_img_adapter = new CustomUnlockedImageAdapter(getContext(),def_images);
            imgAdpt = new ImageAdapter(getContext(),def_images);
        }




        // Toast.makeText(this, list.get(0).getName(), Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.recycler_main2);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(unlocked_img_adapter);


        SharedPreferences sharedPref= getContext().getSharedPreferences("Points", 0);
        TextView points = (TextView) view.findViewById(R.id.points2);
        Long number = sharedPref.getLong("rewards", 0);
        points.setText(String.valueOf(number));

        GridView grid = view.findViewById(R.id.grid2);
//            ImageAdapter imgAdpt = new ImageAdapter(this);
//            grid.setAdapter(imgAdpt);

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

        return view;


    }

    private void loadRewardedAd(){
        if (mRewardedAd.isLoaded()) {
            Activity activityContext = getActivity();
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    // Ad closed.
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Toast.makeText(getContext(), "YOU HAVE EARNED 50 REWARD POINTS", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("Points",0);
                    Long s1 = sharedPreferences.getLong("rewards",0) + 50;

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putLong("rewards", s1);
                    myEdit.commit();
                    startActivity(getActivity().getIntent());
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