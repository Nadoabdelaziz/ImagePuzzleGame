package dragosholban.com.androidpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {

    AudioManager manager;
    private boolean VolIsMute;
    int currentVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        VolIsMute = false;

        Button help = (Button) findViewById(R.id.help);
        Button about = (Button) findViewById(R.id.about);


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,AboutUsActivity.class);
                startActivity(intent);
            }
        });
        ImageView mNotification_on_btn=(ImageView)findViewById(R.id.on_btn);
        ImageView mNotification_off_btn=(ImageView)findViewById(R.id.off_btn);

        SharedPreferences preff = this.getSharedPreferences("Sound",Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = preff.edit();
        String s1 = preff.getString("status", "");



//        myEdit.putString("status","ON" );
//        myEdit.commit();

        if(s1.equals("ON")){
            mNotification_off_btn.setVisibility(View.GONE);
            mNotification_on_btn.setVisibility(View.VISIBLE);
        }
        else {
            mNotification_on_btn.setVisibility(View.GONE);
            mNotification_off_btn.setVisibility(View.VISIBLE);
        }


        mNotification_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotification_on_btn.setVisibility(View.GONE);
                mNotification_off_btn.setVisibility(View.VISIBLE);
//                mute();
                onmute(v);
//                myEdit.putString("status","OFF" );
//                myEdit.commit();
                Log.d("TAG", "onClick: Sounds OFF");
            }
        });
        mNotification_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotification_off_btn.setVisibility(View.GONE);
                mNotification_on_btn.setVisibility(View.VISIBLE);
//                unmute();
                onmute(v);
//                myEdit.putString("status","ON" );
//                myEdit.commit();
                Log.d("TAG", "onClick: Sounds ON");
            }
        });
    }

    public void isMute() {
        SharedPreferences preff = this.getSharedPreferences("Sound",Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = preff.edit();
        if (VolIsMute) {
//            Toast.makeText(this, "should be unmuted now", Toast.LENGTH_SHORT).show();
            manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            VolIsMute = false;
            myEdit.putString("status","OFF" );
            myEdit.commit();
        } else {
//            Toast.makeText(this, "should be muted now", Toast.LENGTH_SHORT).show();
            manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            VolIsMute = true;
            myEdit.putString("status","OFF" );
            myEdit.commit();
        }
    }

    public void onmute(View view) {
        SharedPreferences preff = this.getSharedPreferences("Sound",Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = preff.edit();
        currentVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (currentVolume == 0) {
//            Toast.makeText(getApplicationContext(),
//                    " Volume is " + currentVolume +"Press unmute", Toast.LENGTH_SHORT).show();
            manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            VolIsMute = false;
            myEdit.putString("status","ON" );
            myEdit.commit();
        } else {
            isMute();
        }
    }


//    private void mute() {
//        NotificationManager n = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!n.isNotificationPolicyAccessGranted()) {
//
//                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                alertDialog.setTitle("You must turn on Don't Disturb Permission for this app ! \n");
//
//                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Turn On",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//                                startActivity(intent);
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
//
//            }
//            else{
//                Log.i("onToggleClicked", "ToggleClick Event Started");
//                //an AudioManager object, to change the volume settings
//                AudioManager amanager;
//                amanager = (AudioManager) getSystemService(AUDIO_SERVICE);
//                // Is the toggle on?
//                boolean on = true;
//                //mute audio
//                //        AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                //        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
//                Log.i("onToggleIsChecked", "ToggleClick Is On");
//                //turn ringer silent
//                amanager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                Log.i("RINGER_MODE_SILENT", "Set to true");
//
//                //turn off sound, disable notifications
//                amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
//                Log.i("STREAM_SYSTEM", "Set to true");
//                //notifications
//                amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
//                Log.i("STREAM_NOTIFICATION", "Set to true");
//                //alarm
//                amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
//                Log.i("STREAM_ALARM", "Set to true");
//                //ringer
//                amanager.setStreamMute(AudioManager.STREAM_RING, true);
//                Log.i("STREAM_RING", "Set to true");
//                //media
//                amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
//                Log.i("STREAM_MUSIC", "Set to true");
//            }
//        }
//        }
//
//        public void unmute() {
//            NotificationManager n = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!n.isNotificationPolicyAccessGranted()) {
//
//                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                    alertDialog.setTitle("You must turn on Don't Disturb Permission for this app ! \n");
//
//                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Turn On",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//                                    startActivity(intent);
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//                }
//                else{
//                    Log.i("onToggleClicked", "ToggleClick Event Started");
//                    //an AudioManager object, to change the volume settings
//                    AudioManager amanager;
//                    amanager = (AudioManager) getSystemService(AUDIO_SERVICE);
//                    // Is the toggle on?
//                    boolean on = false;
//
//                    Log.i("onToggleIsChecked", "ToggleClick Is Off");
//
//                    //turn ringer silent
//                    amanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                    Log.i(".RINGER_MODE_NORMAL", "Set to true");
//
//                    // turn on sound, enable notifications
//                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
//                    Log.i("STREAM_SYSTEM", "Set to False");
//                    //notifications
//                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
//                    Log.i("STREAM_NOTIFICATION", "Set to False");
//                    //alarm
//                    amanager.setStreamMute(AudioManager.STREAM_ALARM, false);
//                    Log.i("STREAM_ALARM", "Set to False");
//                    //ringer
//                    amanager.setStreamMute(AudioManager.STREAM_RING, false);
//                    Log.i("STREAM_RING", "Set to False");
//                    //media
//                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
//                    Log.i("STREAM_MUSIC", "Set to False");
////        //unmute audio
////        AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
////        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
//                }
//            }
//    }
}