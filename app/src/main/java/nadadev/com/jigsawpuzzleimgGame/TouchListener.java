package nadadev.com.jigsawpuzzleimgGame;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

import nadadev.com.jigsawpuzzleimgGame.R;

public class TouchListener implements View.OnTouchListener {
    private float xDelta;
    private float yDelta;
    private PuzzleActivity activity;
    Context context;

    public int count;

    public TouchListener(PuzzleActivity activity) {
        this.activity = activity;
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        final MediaPlayer mp = MediaPlayer.create(this.activity, R.raw.coin);
        SharedPreferences sh = this.activity.getSharedPreferences("SOUND", Context.MODE_PRIVATE);
        Boolean sound = sh.getBoolean("Sounds",true);

        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();
        final double tolerance = sqrt(pow(view.getWidth(), 2) + pow(view.getHeight(), 2)) / 10;

        PuzzlePiece piece = (PuzzlePiece) view;
        if (!piece.canMove) {
            return true;
        }

        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                piece.bringToFront();
                break;
            case MotionEvent.ACTION_MOVE:
                lParams.leftMargin = (int) (x - xDelta);
                lParams.topMargin = (int) (y - yDelta);
                view.setLayoutParams(lParams);
                break;
            case MotionEvent.ACTION_UP:
                count++;
//                Toast.makeText(activity, String.valueOf(count), Toast.LENGTH_SHORT).show();
                int xDiff = abs(piece.xCoord - lParams.leftMargin);
                int yDiff = abs(piece.yCoord - lParams.topMargin);
                if (xDiff <= tolerance && yDiff <= tolerance) {
                    lParams.leftMargin = piece.xCoord;
                    lParams.topMargin = piece.yCoord;
                    piece.setLayoutParams(lParams);
                    piece.canMove = false;



                    if(sound) {
                        mp.start();
                    }

                    Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
                    animation.setDuration(150); //1 second duration for each animation cycle
                    animation.setInterpolator(new LinearInterpolator());
                    animation.setRepeatCount(2); //repeating indefinitely
                    animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
                    piece.startAnimation(animation);
                    sendViewToBack(piece);

                    activity.checkGameOver();
                }
                break;
        }

        return true;
    }

    public void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    public int getCount() {
        return count;
    }
}