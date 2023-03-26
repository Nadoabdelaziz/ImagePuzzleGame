package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CustomUnlockedImageAdapter extends  RecyclerView.Adapter<CustomUnlockedImageViewHolder> {
    private Context context;
    private AssetManager am;
    private String[] files;
    public CustomUnlockedImageAdapter(Context context,String[]unlocked_images) {
        this.context = context;
        this.files = unlocked_images;
//        this.am = context.getAssets();
//        try {
//            files  = am.list("img");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @NonNull
    @Override
    public CustomUnlockedImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomUnlockedImageViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_element2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomUnlockedImageViewHolder holder, int position) {

        // Picasso.get().load("img/"+this.files[position]).into(holder.imgView);
        //Toast.makeText(context,this.files[position], Toast.LENGTH_SHORT).show();
//        Bitmap bitmap = getPicFromAsset( holder.imgView,this.files[position]);
//        holder.imgView.setImageBitmap(bitmap);

//        Uri path = Uri.parse("file:///android_asset/img/women.png");
//        String newPath = path.toString();
//        File f = new File(newPath);
//        if (f == null){
//            Toast.makeText(context,"null name", Toast.LENGTH_SHORT).show();
//        }
//        Toast.makeText(context,f.getName(), Toast.LENGTH_SHORT).show();

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


        //InputStream ims = this.am.open();
       // Picasso.get().load(id).into(holder.imgView);
//        try {
//            File f = new File("img/"+this.files[position]);
//            //InputStream ims = this.am.open();
//            Picasso.get().load(f).into(holder.imgView);
//           // Bitmap myBitmap  = BitmapFactory.decodeStream( ims );
//            //Drawable d = Drawable.createFromStream(ims, null);
//            //holder.imgView.setImageBitmap(myBitmap);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        //Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(holder.imgView);






//        try {
//
////            Bitmap myBitmap  = BitmapFactory.decodeStream(this.am.open("img/"+this.files[position]) );
////            Toast.makeText(context, "img/"+this.files[position], Toast.LENGTH_SHORT).show();
////            holder.imgView.setImageBitmap(myBitmap);
//
////            InputStream ims = this.am.open("img/"+this.files[position]);
////            Drawable d = Drawable.createFromStream(ims, null);
////            holder.imgView.setImageDrawable(d);
//            // set image to ImageView
//        } catch (IOException e) {
//
//            throw new RuntimeException(e);
//        }


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
