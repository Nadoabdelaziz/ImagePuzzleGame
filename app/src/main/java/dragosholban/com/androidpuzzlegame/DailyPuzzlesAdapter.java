package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

public class DailyPuzzlesAdapter extends  RecyclerView.Adapter<DailyPuzzlesViewHolder>{
    private Context context;
    private AssetManager am;
    private String[] files;

    public DailyPuzzlesAdapter(Context context,String[]DailyPuzzles) {
        this.context = context;
        this.files = DailyPuzzles;
    }

    @NonNull
    @Override
    public DailyPuzzlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DailyPuzzlesViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_date_element,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DailyPuzzlesViewHolder holder, int position) {

        String image_fullname = this.files[position];
        String[]  img_no_extension  = image_fullname.split("\\.");
        //Toast.makeText(context,img_no_extension[0], Toast.LENGTH_SHORT).show();
        int id = context.getResources().getIdentifier("dragosholban.com.androidpuzzlegame:drawable/"+img_no_extension[0].toLowerCase(), null,null);
        //Toast.makeText(context,Integer.toString(id), Toast.LENGTH_SHORT).show();
        Picasso.get().load(id).fit() // the image will only be resized if it's bigger than 2048x 1600 pixels.
                .into(holder.imgView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LevelSelectionActivity.class);
                intent.putExtra("assetName", files[position % files.length]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.files.length;
    }

    private Bitmap getPicFromAsset(ImageView imageView, String assetName) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        if(targetW == 0 || targetH == 0) {
            // view has no dimensions set
            return null;
        }

        try {
            InputStream is = am.open("img/" + assetName);
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            is.reset();

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            return BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

}
