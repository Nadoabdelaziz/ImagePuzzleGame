package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Adib on 13-Apr-17.
 */

public class SecondFragment extends Fragment {
    protected View mView;
    RecyclerView recyclerView;
    Context context;
    ImageAdapter imgAdpt;
    DailyPuzzlesAdapter dailyPuzzlesAdapter;
    TextView month;
    ImageView Settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_second, container, false);
        this.mView = view;


        Settings = (ImageView) view.findViewById(R.id.settingsbtn2);

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                Intent intent =new Intent(getContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });


        month = (TextView)  view.findViewById(R.id.monthtxt);
        Calendar cal = Calendar.getInstance();
        month.setText(new SimpleDateFormat("MMM").format(cal.getTime()) + " 2023");

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

        SharedPreferences preff = getContext().getSharedPreferences("Unlocked", Context.MODE_PRIVATE);
        Set<String> new_st = preff.getStringSet("unlocked_images",null);
        if( new_st != null){ //there are saved data
            String[] saved_images =new_st.toArray(new String[new_st.size()]) ;
            dailyPuzzlesAdapter = new DailyPuzzlesAdapter(getContext(),saved_images);
            imgAdpt = new ImageAdapter(getContext(),saved_images,saved_images);
        }
        else { //save 3 defualt images
            SharedPreferences.Editor  editorr= preff.edit();
            editorr.putStringSet("unlocked_images",def_unclocked);
            editorr.commit();
            String [] def_images = def_unclocked.toArray(new String[def_unclocked.size()]) ;
            dailyPuzzlesAdapter = new DailyPuzzlesAdapter(getContext(),def_images);
            imgAdpt = new ImageAdapter(getContext(),def_images,def_images);
        }




        // Toast.makeText(this, list.get(0).getName(), Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.recycler_main3);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dailyPuzzlesAdapter);


        SharedPreferences sharedPref= getContext().getSharedPreferences("Points", 0);
        TextView points = (TextView) view.findViewById(R.id.points3);
        Long number = sharedPref.getLong("rewards", 0);
        points.setText(String.valueOf(number));

        GridView grid = view.findViewById(R.id.grid3);
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


}