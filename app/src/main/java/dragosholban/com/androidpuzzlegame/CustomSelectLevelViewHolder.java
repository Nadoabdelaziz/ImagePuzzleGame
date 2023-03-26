package dragosholban.com.androidpuzzlegame;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomSelectLevelViewHolder extends RecyclerView.ViewHolder{



    public TextView score, puzzle_no;
    public CardView cardView;



    public CustomSelectLevelViewHolder(@NonNull View itemView) {
        super(itemView);
        score = itemView.findViewById(R.id.score8);//prodname
        puzzle_no = itemView.findViewById(R.id.pieces8);//prodprice
        cardView  =  itemView.findViewById(R.id.main_container3);

    }
}
