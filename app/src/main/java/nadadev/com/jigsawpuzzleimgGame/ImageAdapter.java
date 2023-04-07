package nadadev.com.jigsawpuzzleimgGame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;

import nadadev.com.jigsawpuzzleimgGame.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private AssetManager am;
    private String[] files;
    private String [] unlocked_images;
    private String[] DailyPuzzles;
    private boolean progress;
    private String [] levels;

    private String category_name;
    private String [] newfiles;


    public ImageAdapter(Context c, String [] unlocked_images, Boolean progress, String[] levels) {
        mContext = c;
        this.unlocked_images = unlocked_images;
        am = mContext.getAssets();
        files  = unlocked_images;
        this.levels =levels;
        this.progress = progress;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public ImageAdapter(Context c,String [] unlocked_images, String category_name) {
        mContext = c;
        this.unlocked_images = unlocked_images;
        am = mContext.getAssets();
        this.category_name = category_name;
        try {
            files  = am.list("img");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!category_name.equals("All Puzzles")) {
            Log.d("TAG", "CAT nAME ImageAdapter: "+category_name);
            for (int i = 0; i < files.length; i++) {
                if (!files[i].contains(category_name.toLowerCase())) {
//                newfiles[i]=files[i];
                    Log.d("TAG", "Index ImageAdapter: " + files[i]);
                    files[i] = null;
                }
//            files = null;
//            files = newfiles;
            }
            files = removeNull(files);
        }
        Log.d("TAG", "ImageAdapter: " + Arrays.asList(files));

    }

    public String[] removeNull(String[] a) {
        String[] tmp = new String[a.length];
        int counter = 0;
        for (String s : a) {
            if (s != null) {
                tmp[counter++] = s;
            }
        }
        String[] ret = new String[counter];
        System.arraycopy(tmp, 0, ret, 0, counter);
        return ret;
    }

    public ImageAdapter(Context c,String [] unlocked_images) {
        mContext = c;
        this.unlocked_images = unlocked_images;
        am = mContext.getAssets();
        try {
            files  = am.list("img");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageAdapter(Context c,String [] unlocked_images,String [] DailyPuzzles) {
        mContext = c;
        this.unlocked_images = unlocked_images;
        this.DailyPuzzles=DailyPuzzles;

        // here ***
        am = mContext.getAssets();
        try {
            files  = am.list("img");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return files.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        int [] scores={20,40,60,80,100,120};

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_element, null);
        }

        final ImageView imageView = convertView.findViewById(R.id.gridImageview);
        TextView img_statues = convertView.findViewById(R.id.btnedit);
        imageView.setImageBitmap(null);
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences sh = mContext.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mp = MediaPlayer.create(mContext, R.raw.coinsound);
                final MediaPlayer mpalert = MediaPlayer.create(mContext, R.raw.error);

                Boolean sound = sh.getBoolean("Sounds",true);


                String getText = (String) img_statues.getText();

                SharedPreferences sharedPrefPoints= mContext.getSharedPreferences("Points", 0);
                Long number = sharedPrefPoints.getLong("rewards", 0);


                if(progress){
                    if(sound) {
                        mp.start();
                    }
                    Log.d("TAG", "All Images onClick: "+ unlocked_images);
                    Log.d("TAG", "onClick: "+unlocked_images[position]);
                    Log.d("TAG", "onClick: "+levels[position]);
                    Intent intent = new Intent(mContext.getApplicationContext(), PuzzleActivity.class);
                    intent.putExtra("assetname", unlocked_images[position]);
                    intent.putExtra("levelname",levels[position]);
                    intent.putExtra("rewards",String.valueOf(scores[position]));

                    mContext.startActivity(intent);
                }
                else {
                    if (getText.equals("Unlock")) {

                        if(sound) {
                            mpalert.start();
                        }
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        if (number >= 40) {
                            alertDialog.setTitle("Are you sure you want to unlock this puzzle with  40  ? \n");

                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(mContext, Long.toString(number), Toast.LENGTH_SHORT).show();
                                            SharedPreferences.Editor editor1 = sharedPrefPoints.edit();
                                            editor1.putLong("rewards", number - 40);
                                            editor1.commit();
                                            SharedPreferences sharedPrefPuzzle = mContext.getSharedPreferences("Unlocked", Context.MODE_PRIVATE);
                                            Set<String> new_st = sharedPrefPuzzle.getStringSet("unlocked_images", null);
                                            new_st.add(files[position % files.length]);
                                            SharedPreferences.Editor editorr = sharedPrefPuzzle.edit();
                                            editorr.clear();
                                            editorr.putStringSet("unlocked_images", new_st);
                                            editorr.commit();
                                            //saved_images.
                                            dialog.dismiss();
                                            // here
                                            Intent intent = new Intent(mContext.getApplicationContext(), LevelSelectionActivity.class);
                                            intent.putExtra("assetName", files[position % files.length]);
//                                    context.startActivity(intent);
//                                    Intent intent = new Intent(mContext.getApplicationContext(), MainActivity.class);
                                            mContext.startActivity(intent);

                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(mContext, "No is pressed", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });
                        } else {
                            alertDialog.setTitle("You Don't Have Enough Points !");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
//                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Watch Ad", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                TheFragmnetsActivity Fr= new TheFragmnetsActivity();
//                                Fr.loadRewardedAd(alertDialog);
//                            }
//                        });
                        }
                        alertDialog.show();

                    } else {
                        if(sound) {
                            mp.start();
                        }
                        Intent intent = new Intent(mContext.getApplicationContext(), LevelSelectionActivity.class);
                        intent.putExtra("assetName", files[position % files.length]);
                        mContext.startActivity(intent);
                    }
                }
            }});

            // run image related code after the view was laid out
        imageView.post(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<Void, Void, Void>() {
                    private Bitmap bitmap;
                    @Override
                    protected Void doInBackground(Void... voids) {
                        bitmap = getPicFromAsset(imageView, files[position]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        imageView.setImageBitmap(bitmap);
                        //Toast.makeText(mContext, files[position], Toast.LENGTH_SHORT).show();

                        // just put text above images **
                        if (Arrays.asList(unlocked_images).contains(files[position]))
                        {
                            img_statues.setText("Play");
//                            img_statues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.abc_ic_menu_copy_mtrl_am_alpha, 0, 0, 0);
//                            Drawable drawable = mContext.getDrawable(R.drawable.abc_ic_menu_copy_mtrl_am_alpha);

                            img_statues.setTextColor(mContext.getResources().getColor(R.color.white));
                            img_statues.setBackgroundColor(mContext.getResources().getColor(R.color.purple_500));
                        }
                        else
                        {
                            //set id instead of text and add lock icon instead of text
                            img_statues.setText("Unlock");
                            img_statues.setTextColor(mContext.getResources().getColor(R.color.white));
                            img_statues.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                        }
//
//                        if(files[position].equals("A11.jpg") ||files[position].equals("A12.jpg") )
//                        editbtn.setText("Unlock");


                    }
                }.execute();
            }
        });

        return convertView;
    }

    // put images inside listview (imageview) **
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