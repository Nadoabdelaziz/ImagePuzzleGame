package dragosholban.com.androidpuzzlegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;




public class ThirdFragment extends Fragment {

    protected View mView;
    Context context;

    ListView list;

    String[] maintitle ={
            "All Puzzles","Nature",
            "Landmarks","Colors",
            "Food","Objects","Animals"
            ,"Art","Birds","Flowers",
            "Pets"
    };

//    String[] subtitle ={
//            "Sub Title 1","Sub Title 2",
//            "Sub Title 3","Sub Title 4",
//            "Sub Title 5","Sub Title 1","Sub Title 2",
//            "Sub Title 3","Sub Title 4",
//            "Sub Title 5",""
//    };

    Integer[] imgid={
            R.drawable.all_puzzles,R.drawable.natures_icon,
            R.drawable.landmark_icon,R.drawable.color_icon,
            R.drawable.foods_icon, R.drawable.object_icon,R.drawable.animal_icon,
            R.drawable.arts_icon,R.drawable.bird_icon,
            R.drawable.flower_icon,R.drawable.pet_icon,
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_third, container, false);
            this.mView = view;
            MyListAdapter adapter=new MyListAdapter(getActivity(), maintitle,imgid);
            list=(ListView)view.findViewById(R.id.list);
            list.setAdapter(adapter);

        SharedPreferences sharedPref= getContext().getSharedPreferences("Points", 0);
        TextView points = (TextView) view.findViewById(R.id.points4);
        Long number = sharedPref.getLong("rewards", 0);
        points.setText(String.valueOf(number));



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    // TODO Auto-generated method stub
                        //code specific to first list item
                        Intent intent = new Intent(getContext(), CategoriesActivity.class);
                        intent.putExtra("title",maintitle[position]);
                        startActivity(intent);

                }
            });
        return view;
    }

}