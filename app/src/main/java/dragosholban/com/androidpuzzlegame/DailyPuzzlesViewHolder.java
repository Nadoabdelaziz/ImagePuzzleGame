package dragosholban.com.androidpuzzlegame;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DailyPuzzlesViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgView;
    public CardView cardView;
    TextView dateTxt;

    public DailyPuzzlesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imgView = itemView.findViewById(R.id.gridImageview3);
        this.cardView = itemView.findViewById(R.id.main_container123);
        dateTxt = itemView.findViewById(R.id.datetxt);

        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

//        System.out.println(new SimpleDateFormat("MMM").format(cal.getTime()));
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        dateTxt.setText("Today \n"+dayOfMonthStr+" "+new SimpleDateFormat("MMM").format(cal.getTime()) );

    }
}
