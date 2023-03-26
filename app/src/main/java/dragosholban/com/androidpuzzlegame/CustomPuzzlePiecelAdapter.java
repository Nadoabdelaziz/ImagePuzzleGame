package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class CustomPuzzlePiecelAdapter extends  RecyclerView.Adapter<CustomPuzzlePiecelViewHolder>{

    private Context context;
    private ArrayList<PuzzlePiece> pieces;
    private String mCurrentPhotoUri,mCurrentPhotoPath,assetname;


    public CustomPuzzlePiecelAdapter(Context context,ArrayList<PuzzlePiece> pieces) {
        this.context = context;
        this.pieces = pieces;

    }








    @NonNull
    @Override
    public CustomPuzzlePiecelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomPuzzlePiecelViewHolder holder, int position) {

//
//        TouchListener touchListener = new TouchListener(context);
//        for (PuzzlePiece piece : pieces) {
//            piece.setOnTouchListener(touchListener);
//            layout.addView(piece);
//            // randomize position, on the bottom of the screen
//            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();
//            lParams.leftMargin = new Random().nextInt(layout.getWidth() - piece.pieceWidth);
//            lParams.topMargin = layout.getHeight() - piece.pieceHeight;
//            piece.setLayoutParams(lParams);
//        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
