package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import dragosholban.com.androidpuzzlegame.R;

public class MyCollectionsFragment extends Fragment {
    protected View mView;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.my_collections, viewGroup, false);
        this.mView = view;

        ImageView lineColorCode = (ImageView)view.findViewById(R.id.a);
        int color = Color.parseColor("#9C27B0"); //The color u want
        lineColorCode.setColorFilter(color);
        return view;
    }
}
