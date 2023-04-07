package nadadev.com.jigsawpuzzleimgGame;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import nadadev.com.jigsawpuzzleimgGame.R;

class CustomUnlockedImageViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgView;
    public CardView cardView;
    public CustomUnlockedImageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imgView = itemView.findViewById(R.id.gridImageview2);
        this.cardView = itemView.findViewById(R.id.main_container12);
    }
}
