package dragosholban.com.androidpuzzlegame;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomPuzzlePiecelViewHolder extends RecyclerView.ViewHolder{

    public ImageView img;
    public CustomPuzzlePiecelViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.puzzle9);
    }
}
