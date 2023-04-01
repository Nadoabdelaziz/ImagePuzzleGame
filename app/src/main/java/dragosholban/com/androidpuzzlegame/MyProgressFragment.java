package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyProgressFragment extends Fragment {
    protected View mView;
    Context context;
    ImageAdapter imgAdpt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.my_progress, viewGroup, false);
        this.mView = view;

        SharedPreferences sh = getActivity().getSharedPreferences("Progress", Context.MODE_PRIVATE);
//        Set<String> new_st = sh.getStringSet("current_puzzles", null);
//
//        if(!new_st.isEmpty()) {
//            Log.d("TAG", "Size is: "+new_st.size());
//            String[] saved_images = new_st.toArray(new String[new_st.size()]);
//            imgAdpt = new ImageAdapter(getContext(), saved_images, true);
//            GridView grid = view.findViewById(R.id.gridProgress);
////            ImageAdapter imgAdpt = new ImageAdapter(this);
////            grid.setAdapter(imgAdpt);
//            grid.setAdapter(imgAdpt);
//        }

        Map<String, String> outputMap = new HashMap<>();

        try {
            if (sh != null) {
                String jsonString = sh.getString("current_puzzles_Map", (new JSONObject()).toString());
                if (jsonString != null) {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    Iterator<String> keysItr = jsonObject.keys();
                    while (keysItr.hasNext()) {
                        String key = keysItr.next();
                        String value = jsonObject.getString(key);
                        outputMap.put(key, value);
                    }
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        if(outputMap.isEmpty()){
            Log.d("TAG", "tl3t empty ");
        }

        for (Map.Entry<String,String> entry : outputMap.entrySet()){
            Log.d("TAG", "Element Is : "+ entry.getKey());
            Log.d("TAG", "Element Level Is : "+ entry.getValue());
        }

        //        Set<String> new_st = sh.getStringSet("current_puzzles", null);
//
        if(!outputMap.isEmpty()) {
            Log.d("TAG", "Size is: "+outputMap.size());
            String[] saved_images = outputMap.keySet().toArray(new String[outputMap.size()]);
            String[] saved_levels = outputMap.values().toArray(new String[outputMap.size()]);
            imgAdpt = new ImageAdapter(getContext(), saved_images, true,saved_levels);
            GridView grid = view.findViewById(R.id.gridInProgress);
//            ImageAdapter imgAdpt = new ImageAdapter(this);
//            grid.setAdapter(imgAdpt);
            grid.setAdapter(imgAdpt);
        }
//
//        String puzzle = sh.getString("puzzle", "");
//        String lvl = sh.getString("level","");

//        TextView tv = (TextView) view.findViewById(R.id.textViewLoaded);
//        tv.setText(new_st.toString());
        return view;
    }
}