package nadadev.com.jigsawpuzzleimgGame;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import nadadev.com.jigsawpuzzleimgGame.R;


public class FourthFragment extends Fragment{
    protected View mView;
//    TabLayout tabLayout;
//    ViewPager viewPager;
//    TabsAdapter tabsAdapter;
//zizo
    NavigationView navigationView;
    //This is our viewPager
    private ViewPager viewPager;

    MyPuzzlesFragment firstFr;
    MyCollectionsFragment SecFr;
    MyProgressFragment ThirdFr;

    MenuItem prevMenuItem;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        this.mView = view;

        //Initializing viewPager
        viewPager = (ViewPager) view.findViewById(R.id.Tabsviewpager);


        Toolbar toolbar = (Toolbar)view.findViewById(R.id.tool_bar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("My Puzzles"));
        tabLayout.addTab(tabLayout.newTab().setText("My Collections"));
        tabLayout.addTab(tabLayout.newTab().setText("In Progress"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager =(ViewPager)view.findViewById(R.id.Tabsviewpager);
        TabsAdapter tabsAdapter = new TabsAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SharedPreferences sh = view.getContext().getSharedPreferences("SOUND", Context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                final MediaPlayer mpalert = MediaPlayer.create(view.getContext(), R.raw.coinsound);
                Boolean sound = sh.getBoolean("Sounds",true);

                if(sound) {
                    mpalert.start();
                }
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


}
