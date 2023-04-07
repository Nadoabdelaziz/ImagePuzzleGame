package nadadev.com.jigsawpuzzleimgGame;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nadadev.com.jigsawpuzzleimgGame.R;

public class CustomPuzzlePiecelViewHolder extends RecyclerView.ViewHolder{

    public ImageView img;
    public CustomPuzzlePiecelViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.puzzle9);
    }
}
