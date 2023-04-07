package nadadev.com.jigsawpuzzleimgGame;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.constraint.ConstraintLayout;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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

import nadadev.com.jigsawpuzzleimgGame.R;


public class MainActivity extends AppCompatActivity {

//    //zizo
//    BottomNavigationView bottomNavigationView;
//    //This is our viewPager
//    private ViewPager viewPager;
//
//    FirstFragment firstFr;
//    SecondFragment SecFr;
//    ThirdFragment ThirdFr;
//    MenuItem prevMenuItem;

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

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //zizo
//
//        //Initializing viewPager
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//        //Initializing the bottomNavigationView
//        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.person:
//                                viewPager.setCurrentItem(0);
//                                Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
//                                break;
//                            case R.id.home:
//                                viewPager.setCurrentItem(1);
//                                Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
//
//                                break;
//                            case R.id.settings:
//                                viewPager.setCurrentItem(2);
//                                Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
//
//                                break;
//                        }
//                        return false;
//                    }
//                });
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (prevMenuItem != null) {
//                    prevMenuItem.setChecked(false);
//                }
//                else
//                {
//                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                }
//                Log.d("page", "onPageSelected: "+position);
//                bottomNavigationView.getMenu().getItem(position).setChecked(true);
//                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        setupViewPager(viewPager);






        //Toast.makeText(this, "Some fields not entered", Toast.LENGTH_SHORT).show();
        mRewardedAd = new RewardedAd(this,
                "ca-app-pub-2145987647160470/2803949040");

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

        mButton = findViewById(R.id.adwatch);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRewardedAd();
            }
        });


//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        bottomNavigationView.setSelectedItemId(R.id.person);



//        SharedPreferences.Editor editor= sharedPref.edit();
//        editor.putLong("rewards", 1000);
//        editor.commit();


//        String Pts = "125";
//        SharedPreferences mPrefs = getSharedPreferences(Pts, 0);
//        String mString = mPrefs.getString("tag", "default_value_if_variable_not_found");


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
        //    Toast.makeText(MainActivity.this, Integer.toString(st.size()), Toast.LENGTH_SHORT).show();

        SharedPreferences preff = getSharedPreferences("Unlocked",Context.MODE_PRIVATE);
        Set<String> new_st = preff.getStringSet("unlocked_images",null);
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




        // Toast.makeText(this, list.get(0).getName(), Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(unlocked_img_adapter);


            SharedPreferences sharedPref= getSharedPreferences("Points", 0);
            TextView points = (TextView) findViewById(R.id.points);
            Long number = sharedPref.getLong("rewards", 0);
            points.setText(String.valueOf(number));

            GridView grid = findViewById(R.id.grid);
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

    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        firstFr=new FirstFragment();
//        SecFr=new SecondFragment();
//        ThirdFr=new ThirdFragment();
//        adapter.addFragment(firstFr);
//        adapter.addFragment(SecFr);
//        adapter.addFragment(ThirdFr);
//        viewPager.setAdapter(adapter);
//    }

    private void loadRewardedAd(){
        if (mRewardedAd.isLoaded()) {
            Activity activityContext = MainActivity.this;
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
                    Toast.makeText(MainActivity.this, "YOU HAVE EARNED 1000 REWARD POINTS", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("Points",0);
                    Long s1 = sharedPreferences.getLong("rewards",0) + 1000;

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putLong("rewards", s1);
                    myEdit.commit();
                    startActivity(getIntent());
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


//    FirstFragment firstFragment = new FirstFragment();
//    SecondFragment secondFragment = new SecondFragment();
//    ThirdFragment thirdFragment = new ThirdFragment();
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.person:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
//                return true;
//
//            case R.id.home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
//                return true;
//
//            case R.id.settings:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
//                return true;
//        }
//        return false;
//    }

    public void onImageFromCameraClick(View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Are you sure you want to unlock this puzzle with  1000  ? \n");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPrefPoints= MainActivity.this.getSharedPreferences("Points", 0);
                        Long number = sharedPrefPoints.getLong("rewards", 0);
                        SharedPreferences.Editor  editor1= sharedPrefPoints.edit();
                        editor1.putLong("rewards",number-1000);
                        editor1.commit();
                        dialog.dismiss();


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                            }

                            if (photoFile != null) {
                                Uri photoUri = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                            }
                        }

                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "No is pressed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    private File createImageFile() throws IOException {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // permission not granted, initiate request
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            mCurrentPhotoPath = image.getAbsolutePath(); // save this to use in the intent

            return image;
        }

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onImageFromCameraClick(new View(this));
                }

                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Intent intent = new Intent(this, PuzzleActivity.class);
//            intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
//            startActivity(intent);
            Intent intent = new Intent(this, LevelSelectionActivity.class);
            intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
            startActivity(intent);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
//            Intent intent = new Intent(this, PuzzleActivity.class);
//            intent.putExtra("mCurrentPhotoUri", uri.toString());
//            startActivity(intent);

            Intent intent = new Intent(this, LevelSelectionActivity.class);
            intent.putExtra("mCurrentPhotoUri", uri.toString());
            startActivity(intent);
        }
    }

    public void onImageFromGalleryClick(View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Are you sure you want to unlock this puzzle with  1000  ? \n");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPrefPoints= MainActivity.this.getSharedPreferences("Points", 0);
                        Long number = sharedPrefPoints.getLong("rewards", 0);
                        SharedPreferences.Editor  editor1= sharedPrefPoints.edit();
                        editor1.putLong("rewards",number-1000);
                        editor1.commit();
                        dialog.dismiss();

                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "No is pressed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();


    }



}
