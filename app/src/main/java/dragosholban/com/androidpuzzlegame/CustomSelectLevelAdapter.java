package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

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
        holder.puzzle_no.setText(Integer.toString(pieces[position]));
        holder.score.setText(Integer.toString(scores[position]));
        CardView crd = holder.cardView;
        crd.setOnClickListener(new View.OnClickListener() {
//            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PuzzleActivity.class);
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
