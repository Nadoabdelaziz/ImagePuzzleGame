package nadadev.com.jigsawpuzzleimgGame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nadadev.com.jigsawpuzzleimgGame.R;

public class TheFragmnetsActivity extends AppCompatActivity {

    //zizo
    BottomNavigationView bottomNavigationView;
    //This is our viewPager
    private ViewPager viewPager;

    FirstFragment firstFr;
    SecondFragment SecFr;
    ThirdFragment ThirdFr;
    FourthFragment FourFr;

    MenuItem prevMenuItem;

    String mCurrentPhotoPath;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3;
    static final int REQUEST_IMAGE_GALLERY = 4;

    Activity MyActivity = TheFragmnetsActivity.this;
    Context context = this;
    private RewardedAd mRewardedAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_fragmnets);




        //Toast.makeText(this, "Some fields not entered", Toast.LENGTH_SHORT).show();
//        mRewardedAd = new RewardedAd(context,
//                "ca-app-pub-2145987647160470/2803949040");
        mRewardedAd = new RewardedAd(context,
                "ca-app-pub-2145987647160470/2210632650");

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
//                Toast.makeText(MyActivity, "Loaded Succesfuly", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                Log.d("", "notloaded: "+adError);
                // Ad failed to load.
            }
        };
        mRewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

        //zizo

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);



        bottomNavigationView.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        SharedPreferences sh = context.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sh.edit();
                        final MediaPlayer mp = MediaPlayer.create(context, R.raw.coinsound);
                        Boolean sound = sh.getBoolean("Sounds",true);

                        if(sound) {
                            mp.start();
                        }
                        switch (item.getItemId()) {
                            case R.id.person:
                                viewPager.setCurrentItem(0);
                                Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
                                break;

                            case R.id.home:
                                viewPager.setCurrentItem(1);
                                Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
                                break;

                            case R.id.settings:
                                viewPager.setCurrentItem(2);
                                Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
                                break;

                            case R.id.settings2:
                                viewPager.setCurrentItem(3);
                                Log.d("Viewpager", "onNavigationItemSelected: "+ viewPager.getCurrentItem());
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {

                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                bottomNavigationView.getMenu().getItem(1).setTitleCondensed("true");
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        startActivity(getIntent());
    }

    public void onImageFromCameraClick2(View view) {
        SharedPreferences sharedPrefPoints= MyActivity.getSharedPreferences("Points", 0);
        Long number = sharedPrefPoints.getLong("rewards", 0);



        SharedPreferences sh = MyActivity.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        final MediaPlayer mpalert = MediaPlayer.create(MyActivity, R.raw.error);
        Boolean sound = sh.getBoolean("Sounds",true);

        if(sound) {
            mpalert.start();
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(MyActivity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        if(number >= 40) {

                            alertDialog.setTitle("Are you sure you want to unlock this puzzle with  40  ? \n");

                            File finalPhotoFile = photoFile;
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            SharedPreferences sharedPrefPoints = MyActivity.getSharedPreferences("Points", 0);
                                            Long number = sharedPrefPoints.getLong("rewards", 0);
                                            SharedPreferences.Editor editor1 = sharedPrefPoints.edit();
                                            editor1.putLong("rewards", number - 40);
                                            editor1.commit();
                                            dialog.dismiss();


                                            if (finalPhotoFile != null) {
                                                Uri photoUri = FileProvider.getUriForFile(context, MyActivity.getPackageName() + ".fileprovider", finalPhotoFile);
                                                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                                            }

                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                //                            Toast.makeText(context, "No is pressed", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });
                        }
                        else{
                            alertDialog.setTitle("You Don't Have Enough Points !");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Watch Ad", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    loadRewardedAd(alertDialog);
                                }
                            });
                        }
                        alertDialog.show();

            } catch (IOException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            }

        }

//        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//
//        if(number >= 40) {
//
//            alertDialog.setTitle("Are you sure you want to unlock this puzzle with  40  ? \n");
//
//            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            SharedPreferences sharedPrefPoints = MyActivity.getSharedPreferences("Points", 0);
//                            Long number = sharedPrefPoints.getLong("rewards", 0);
//                            SharedPreferences.Editor editor1 = sharedPrefPoints.edit();
//                            editor1.putLong("rewards", number - 40);
//                            editor1.commit();
//                            dialog.dismiss();
//
//
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            if (intent.resolveActivity(MyActivity.getPackageManager()) != null) {
//                                File photoFile = null;
//                                try {
//                                    photoFile = createImageFile();
//                                } catch (IOException e) {
//                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
//                                }
//
//                                if (photoFile != null) {
//                                    Uri photoUri = FileProvider.getUriForFile(context, MyActivity.getPackageName() + ".fileprovider", photoFile);
//                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//                                }
//                            }
//
//                        }
//                    });
//            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
////                            Toast.makeText(context, "No is pressed", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                        }
//                    });
//        }
//        else{
//            alertDialog.setTitle("You Don't Have Enough Points !");
//            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Watch Ad", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    loadRewardedAd(alertDialog);
//                }
//            });
//        }
//        alertDialog.show();

    }

    private File createImageFile() throws IOException {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // permission not granted, initiate request
            ActivityCompat.requestPermissions(MyActivity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
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
                    onImageFromCameraClick2(new View(context));
                }

                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == MyActivity.RESULT_OK) {
//            Intent intent = new Intent(this, PuzzleActivity.class);
//            intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
//            startActivity(intent);
            Intent intent = new Intent(context, LevelSelectionActivity.class);
            intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
            startActivity(intent);
        }
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == MyActivity.RESULT_OK) {
            Uri uri = data.getData();
//            Intent intent = new Intent(this, PuzzleActivity.class);
//            intent.putExtra("mCurrentPhotoUri", uri.toString());
//            startActivity(intent);

            Intent intent = new Intent(context, LevelSelectionActivity.class);
            intent.putExtra("mCurrentPhotoUri", uri.toString());
            startActivity(intent);
        }
    }

    public void onImageFromGalleryClick2(View view) {
        SharedPreferences sharedPrefPoints= MyActivity.getSharedPreferences("Points", 0);
        Long number = sharedPrefPoints.getLong("rewards", 0);


        SharedPreferences sh = MyActivity.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        final MediaPlayer mpalert = MediaPlayer.create(MyActivity, R.raw.error);
        Boolean sound = sh.getBoolean("Sounds",true);

        if(sound) {
            mpalert.start();
        }


        AlertDialog alertDialog = new AlertDialog.Builder(context).create();



                            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(MyActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
                            } else {
                                if(number >= 40) {
                                    alertDialog.setTitle("Are you sure you want to unlock this puzzle with  40  ? \n");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    SharedPreferences.Editor editor1 = sharedPrefPoints.edit();
                                                    editor1.putLong("rewards", number - 40);
                                                    editor1.commit();
                                                    dialog.dismiss();

                                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                                    intent.setType("image/*");
                                                    startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
                                                }
                                            });
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Toast.makeText(context, "No is pressed", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            });
                                }
                                else {
//                                    startActivity(getIntent());
                                    alertDialog.setTitle("You Don't Have Enough Points !");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Watch Ad", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            loadRewardedAd(alertDialog);
                                        }
                                    });
                                }
                            }

        alertDialog.show();


    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        firstFr=new FirstFragment();
        SecFr=new SecondFragment();
        ThirdFr=new ThirdFragment();
        FourFr = new FourthFragment();
        adapter.addFragment(firstFr);
        adapter.addFragment(SecFr);
        adapter.addFragment(ThirdFr);
        adapter.addFragment(FourFr);
        viewPager.setAdapter(adapter);
    }

    public void loadRewardedAd(Dialog d1){
        if (mRewardedAd.isLoaded()) {
            Activity activityContext = MyActivity;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    d1.dismiss();
                    // Ad closed.
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Toast.makeText(context, "You Earned Reward", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("Points",0);
                    Long s1 = sharedPreferences.getLong("rewards",0) + 50;

                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putLong("rewards", s1);
                    myEdit.commit();
                    startActivity(MyActivity.getIntent());

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