package nadadev.com.jigsawpuzzleimgGame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;

import nadadev.com.jigsawpuzzleimgGame.R;


public class PuzzleActivity extends AppCompatActivity {
    ArrayList<PuzzlePiece> pieces;
    String mCurrentPhotoPath;
    String mCurrentPhotoUri;
    private Context context = this;
    public boolean timeUp=false;
    public long score;
    public int [] dimension = {4,5,6,7,8,9};
    int xy,total_pieces;
    TextView clk;
    CountDownTimer countDownTimer;
    long  timeleft = 60000;
    long [] timeleftarr ={60000,70000,90000,125000,245000,350000};
    boolean timerrunning;

//    Intent intent = getIntent();
//    final String assetName = intent.getStringExtra("assetName");
//    final String level = intent.getStringExtra("levelname");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);


        Intent intent = getIntent();
        String assetName = intent.getStringExtra("assetname");
        String level = intent.getStringExtra("levelname");
        String rewardpts = intent.getStringExtra("rewards");

        Log.d("TAG", "awl points : "+rewardpts);

//        Log.d("TAG", "onCreate Asset Puzzle Name: "+assetName);

        mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath");
        mCurrentPhotoUri = intent.getStringExtra("mCurrentPhotoUri");

        int lvl = Integer.parseInt(level) ;
        timeleft = timeleftarr[lvl-1];
        clk = (TextView) findViewById(R.id.clk);
        if(timerrunning){
            stoptimer();
        }
        else
        {
            startimer();
        }

        final RelativeLayout layout = findViewById(R.id.layout);
        final ImageView imageView = findViewById(R.id.imageView);
        final ImageView pzl_contianer = findViewById(R.id.img_pzl_contanier);

         xy = dimension[lvl-1] ;
         total_pieces = xy * xy;
        if (lvl >4)
            total_pieces = total_pieces - (2*lvl) ;
//        Toast.makeText(this, Integer.toString(total_pieces), Toast.LENGTH_SHORT).show();

        // run image related code after the view was laid out
        // to have all dimensions calculated
        imageView.post(new Runnable() {
            @Override
            public void run() {
                if (assetName != null) {
                    setPicFromAsset(assetName, imageView);
                } else if (mCurrentPhotoPath != null) {
                    setPicFromPath(mCurrentPhotoPath, imageView);
                } else if (mCurrentPhotoUri != null) {
                    imageView.setImageURI(Uri.parse(mCurrentPhotoUri));
                }

                pieces = splitImage(total_pieces,xy,xy);
                // shuffle pieces order
                Collections.shuffle(pieces);

                TouchListener touchListener = new TouchListener(PuzzleActivity.this);
                for (PuzzlePiece piece : pieces)
                    generate_piece(piece,touchListener,layout);


            }
        });

    }

    @Override
    public void onBackPressed() {
        // your stuff here
        stoptimer();
        Log.d("Time", "onBackPressed:");
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Are you sure you want to exit the puzzle  ? \n");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer.cancel();
                        dialog.dismiss();
                        Intent intent = new Intent(PuzzleActivity.this,TheFragmnetsActivity.class);
                        startActivity(intent);
//                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startimer();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }
    public long FinalScore(){
        TouchListener touchListener= new TouchListener(this);
        int nmoves = touchListener.getCount();
        score = nmoves / 5;
        return score;
    }
    public void startimer(){
        countDownTimer = new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long l) {
                timeleft = l;
                updatetimer();
                Log.d("Time", "onTick: "+timeleft);
//                if(isGameOver()){
//                    countDownTimer.cancel();
//                }
            }

            @Override
            public void onFinish() {
                timeUp = true;
                checkGameOver();
            }
        }.start();
    }


    public void stoptimer(){
        countDownTimer.cancel();
        timerrunning = false;
    }

    public void updatetimer(){
        int minutes = (int) timeleft / 60000;
        int seconds = (int) timeleft % 60000 / 1000;

        String timelefttext;

        timelefttext = "" +minutes;
        timelefttext += ":";
        if(seconds < 10) timelefttext += "0";
        timelefttext+=seconds;

        clk.setText(timelefttext);
    }

    private void setPicFromAsset(String assetName, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        AssetManager am = getAssets();
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

            Bitmap bitmap = BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<PuzzlePiece> splitImage(int npieces,int nrows,int ncols) {
        int piecesNumber = npieces;
        int rows = nrows;
        int cols = ncols;

        ImageView imageView = findViewById(R.id.imageView);
        ArrayList<PuzzlePiece> pieces = new ArrayList<>(piecesNumber);

        // Get the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        int[] dimensions = getBitmapPositionInsideImageView(imageView);
        int scaledBitmapLeft = dimensions[0];
        int scaledBitmapTop = dimensions[1];
        int scaledBitmapWidth = dimensions[2];
        int scaledBitmapHeight = dimensions[3];

        int croppedImageWidth = scaledBitmapWidth - 2 * abs(scaledBitmapLeft);
        int croppedImageHeight = scaledBitmapHeight - 2 * abs(scaledBitmapTop);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true);
        Bitmap croppedBitmap = Bitmap.createBitmap(scaledBitmap, abs(scaledBitmapLeft), abs(scaledBitmapTop), croppedImageWidth, croppedImageHeight);

        // Calculate the with and height of the pieces
        int pieceWidth = croppedImageWidth/cols;
        int pieceHeight = croppedImageHeight/rows;

        // Create each bitmap piece and add it to the resulting array
        int yCoord = 0;
        for (int row = 0; row < rows; row++) {
            int xCoord = 0;
            for (int col = 0; col < cols; col++) {
                // calculate offset for each piece
                int offsetX = 0;
                int offsetY = 0;
                if (col > 0) {
                    offsetX = pieceWidth / 3;
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3;
                }

                // apply the offset to each piece
                Bitmap pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY);
                PuzzlePiece piece = new PuzzlePiece(getApplicationContext());
                piece.setImageBitmap(pieceBitmap);
                piece.xCoord = xCoord - offsetX + imageView.getLeft();
                piece.yCoord = yCoord - offsetY + imageView.getTop();
                piece.pieceWidth = pieceWidth + offsetX;
                piece.pieceHeight = pieceHeight + offsetY;

                // this bitmap will hold our final puzzle piece image
                Bitmap puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888);

                // draw path
                int bumpSize = pieceHeight / 4;
                Canvas canvas = new Canvas(puzzlePiece);
                Path path = new Path();
                path.moveTo(offsetX, offsetY);
                if (row == 0) {
                    // top side piece
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                } else {
                    // top bump
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3, offsetY);
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, offsetY);
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                }

                if (col == cols - 1) {
                    // right side piece
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                } else {
                    // right bump
                    path.lineTo(pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                    path.cubicTo(pieceBitmap.getWidth() - bumpSize,offsetY + (pieceBitmap.getHeight() - offsetY) / 6, pieceBitmap.getWidth() - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                }

                if (row == rows - 1) {
                    // bottom side piece
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                } else {
                    // bottom bump
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, pieceBitmap.getHeight());
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5,pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6, pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3, pieceBitmap.getHeight());
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                }

                if (col == 0) {
                    // left side piece
                    path.close();
                } else {
                    // left bump
                    path.lineTo(offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                    path.cubicTo(offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6, offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                    path.close();
                }

                // mask the piece
                Paint paint = new Paint();
                paint.setColor(0XFF000000);
                paint.setStyle(Paint.Style.FILL);

                canvas.drawPath(path, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(pieceBitmap, 0, 0, paint);

                // draw a white border
                Paint border = new Paint();
                border.setColor(0X80FFFFFF);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(8.0f);
                canvas.drawPath(path, border);

                // draw a black border
                border = new Paint();
                border.setColor(0X80000000);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(3.0f);
                canvas.drawPath(path, border);

                // set the resulting bitmap to the piece
                piece.setImageBitmap(puzzlePiece);

                pieces.add(piece);
                xCoord += pieceWidth;
            }
            yCoord += pieceHeight;
        }

        return pieces;
    }

    private int[] getBitmapPositionInsideImageView(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - actH)/2;
        int left = (int) (imgViewW - actW)/2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }

    public void checkGameOver() {
        if (isGameOver()) {
//            SharedPreferences sharedPref= getSharedPreferences("Points", 0);
//            SharedPreferences.Editor editor= sharedPref.edit();
//            String newnumber = sharedPref.getString("number", "");
//
//            editor.putString("number", String.valueOf(Integer.parseInt(newnumber)+1000));
//            editor.commit();
//
            Intent intent = getIntent();
            String assetName = intent.getStringExtra("assetname");
            String level = intent.getStringExtra("levelname");
            String rewards = intent.getStringExtra("rewards");
            Log.d("TAG", "LevelDone "+rewards);

            countDownTimer.cancel();
            if(timeUp == true){
                SharedPreferences sh = this.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(this, R.raw.lose);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                if(mCurrentPhotoPath!=null){
                    intent = new Intent(getApplicationContext(), losegameactivity.class);
                    intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
                    intent.putExtra("levelname",level);
                    intent.putExtra("rewardpts",rewards);
                    long thescore = FinalScore();
                    intent.putExtra("score",String.valueOf(thescore));
                    startActivity(intent);
                }
                else if(mCurrentPhotoUri!=null){
                    intent = new Intent(getApplicationContext(), losegameactivity.class);
                    intent.putExtra("mCurrentPhotoUri", mCurrentPhotoUri);
                    intent.putExtra("levelname",level);
                    intent.putExtra("rewardpts",rewards);
                    long thescore = FinalScore();
                    intent.putExtra("score",String.valueOf(thescore));
                    startActivity(intent);
                }
                else{
                    intent = new Intent(getApplicationContext(), losegameactivity.class);
                    intent.putExtra("assetName", assetName);
                    intent.putExtra("levelname",level);
                    intent.putExtra("rewardpts",rewards);
                    long thescore = FinalScore();
                    intent.putExtra("score",String.valueOf(thescore));
                    startActivity(intent);
                }

            }
            else{
                SharedPreferences sh = this.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(this, R.raw.win);
                Boolean sound = sh.getBoolean("Sounds",true);
                if(sound) {
                    mp.start();
                }
                if(mCurrentPhotoPath!=null) {
                    intent = new Intent(getApplicationContext(), LevelDoneActivity.class);
                    intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
                    intent.putExtra("levelname",level);
                    Log.d("TAG", "checkGameOver: "+rewards);
                    intent.putExtra("rewardpts",rewards);
                    long thescore = FinalScore();
                    intent.putExtra("score",String.valueOf(thescore));
                    startActivity(intent);
//                    Toast.makeText(context, "Photo exists", Toast.LENGTH_SHORT).show();
                }
                else if(mCurrentPhotoUri!=null){
                    intent = new Intent(getApplicationContext(), LevelDoneActivity.class);
                    intent.putExtra("mCurrentPhotoUri", mCurrentPhotoUri);
                    intent.putExtra("levelname",level);
                    Log.d("TAG", "checkGameOver: "+rewards);
                    intent.putExtra("rewardpts",rewards);
                    long thescore = FinalScore();
                    intent.putExtra("score",String.valueOf(thescore));
                    startActivity(intent);
//                    Toast.makeText(context, "Photo exists", Toast.LENGTH_SHORT).show();

                }
                else{
                    intent = new Intent(getApplicationContext(), LevelDoneActivity.class);
                    intent.putExtra("assetName", assetName);
                    intent.putExtra("levelname",level);
                    Log.d("TAG", "checkGameOver: "+rewards);
                    intent.putExtra("rewardpts",rewards);
                    long thescore = FinalScore();
                    intent.putExtra("score",String.valueOf(thescore));
                    startActivity(intent);
                }

            }
        }
    }


    private boolean isGameOver() {

        if(timeUp==true){
            return true;
        }

        for (PuzzlePiece piece : pieces) {
            if (piece.canMove) {
                return false;
            }
        }
        return true;
    }

    private void setPicFromPath(String mCurrentPhotoPath, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap rotatedBitmap = bitmap;

        // rotate bitmap if needed
        try {
            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        imageView.setImageBitmap(rotatedBitmap);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void generate_piece(PuzzlePiece piece,TouchListener touchListener,RelativeLayout layout){
        piece.setOnTouchListener(touchListener);
        layout.addView(piece);
        // randomize position, on the bottom of the screen
        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();
        lParams.leftMargin = new Random().nextInt(layout.getWidth() - piece.pieceWidth);
        lParams.topMargin = layout.getHeight() - piece.pieceHeight;
//                    lParams.bottomMargin = layout.getHeight()-pzl_contianer.getHeight();
        piece.setLayoutParams(lParams);
    }
}
