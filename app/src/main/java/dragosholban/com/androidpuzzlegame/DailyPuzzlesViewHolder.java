package dragosholban.com.androidpuzzlegame;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DailyPuzzlesViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgView;
    public CardView cardView;
    public DailyPuzzlesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imgView = itemView.findViewById(R.id.gridImageview3);
        this.cardView = itemView.findViewById(R.id.main_container123);
    }
}
