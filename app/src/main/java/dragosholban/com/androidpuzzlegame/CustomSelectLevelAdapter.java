package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CustomSelectLevelAdapter  extends  RecyclerView.Adapter<CustomSelectLevelViewHolder>{
    private Context context;
    private String mCurrentPhotoUri,mCurrentPhotoPath,assetname;

    private int levels = 6;
    private int [] scores={20,40,60,150,240,450};
    private int [] pieces={36,64,100,144,225,400};






    public CustomSelectLevelAdapter(Context context,String mCurrentPhotoUri,String mCurrentPhotoPath,String assetname) {
        this.context = context;
        this.mCurrentPhotoUri = mCurrentPhotoUri;
        this.mCurrentPhotoPath = mCurrentPhotoPath;
        this.assetname = assetname;
    }


    @NonNull
    @Override
    public CustomSelectLevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomSelectLevelViewHolder(LayoutInflater.from(context).inflate(R.layout.select_level_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomSelectLevelViewHolder holder, int position) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("Progress", Context.MODE_PRIVATE);


        holder.puzzle_no.setText(Integer.toString(pieces[position]));
        holder.score.setText(Integer.toString(scores[position]));
        CardView crd = holder.cardView;
        crd.setOnClickListener(new View.OnClickListener() {
//            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PuzzleActivity.class);

                Set<String> new_st = sharedPreferences.getStringSet("current_puzzles", null);
                String Map = sharedPreferences.getString("current_puzzles_Map", null);

                if(Map != null){
//                    Log.d("TAG", "msh null: ");
//                    new_st.add(assetname);
//                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                    myEdit.clear();
//                    myEdit.putStringSet("current_puzzles", new_st);
//                    myEdit.commit();
                    HashMap<String,String> outputMap = new HashMap<>();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(Map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Iterator<String> keysItr = jsonObject.keys();
                        while (keysItr.hasNext()) {
                            String key = keysItr.next();
                            String value = null;
                            try {
                                value = jsonObject.getString(key);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            outputMap.put(key,value);
                        }
                    outputMap.put(assetname,Integer.toString(position+1));

                    JSONObject newjsonObject = new JSONObject(outputMap);
                    String jsonString = newjsonObject.toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("current_puzzles_Map", jsonString);
                    editor.commit();
                }

                else{
                    Log.d("TAG", "tl3t null: ");
                    HashMap<String,String> progressMap = new HashMap<>();
                    progressMap.put(assetname,Integer.toString(position+1));

                    JSONObject jsonObject = new JSONObject(progressMap);
                    String jsonString = jsonObject.toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("current_puzzles_Map", jsonString);
                    editor.commit();
//                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                    for (String s : progressMap.keySet()) {
//                        myEdit.putString(s, progressMap.get(s));
//                    }
//                    myEdit.clear();
////                    myEdit.put("current_puzzles", new_st2);
//                    myEdit.commit();
                }


//                Set<String> new_st = sharedPreferences.putStringSet("current_puzzles", null);
//                Log.d("TAG", "onClick: "+new_st);
//                myEdit.clear();
//                SharedPreferences sh = context.getSharedPreferences("Progress", Context.MODE_PRIVATE);
//                Set<String> progressStr = sh.getStringSet("current_puzzles", null);

//                if(progressStr != null) {
//                    progressStr.add(assetname);
//                    myEdit.putStringSet("current_puzzles", progressStr);
//                    myEdit.commit();
//                }

                if(mCurrentPhotoPath!=null){
                    intent.putExtra("levelname", Integer.toString(position+1));
                    intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
                    context.startActivity(intent);
                }
                else if(mCurrentPhotoUri !=null){
                    intent.putExtra("levelname", Integer.toString(position+1));
                    intent.putExtra("mCurrentPhotoUri", mCurrentPhotoUri);
                    context.startActivity(intent);
                }
                else{
                    intent.putExtra("levelname", Integer.toString(position+1));
                    intent.putExtra("assetname", assetname);
                    context.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return this.levels;
    }
}
