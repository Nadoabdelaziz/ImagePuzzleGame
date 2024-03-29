package nadadev.com.jigsawpuzzleimgGame;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabsAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                MyPuzzlesFragment mypuzzles = new MyPuzzlesFragment();
                return mypuzzles;
            case 1:
                MyCollectionsFragment mycollection = new MyCollectionsFragment();
                return mycollection;
            case 2:
                MyProgressFragment inprogress = new MyProgressFragment();
                return inprogress;
            default:
                return null;
        }
    }
}