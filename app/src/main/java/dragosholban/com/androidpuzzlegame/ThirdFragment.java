package dragosholban.com.androidpuzzlegame;

import android.content.Context;
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
            "Title 1","Title 2",
            "Title 3","Title 4",
            "Title 5",
    };

    String[] subtitle ={
            "Sub Title 1","Sub Title 2",
            "Sub Title 3","Sub Title 4",
            "Sub Title 5",
    };

    Integer[] imgid={
            R.drawable.calender,R.drawable.multi,
            R.drawable.lock,R.drawable.timesup,
            R.drawable.squares,
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_third, container, false);
            this.mView = view;
            MyListAdapter adapter=new MyListAdapter(getActivity(), maintitle, subtitle,imgid);
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
                    if(position == 0) {
                        //code specific to first list item
                        Toast.makeText(getContext(),"Place Your First Option Code",Toast.LENGTH_SHORT).show();
                    }

                    else if(position == 1) {
                        //code specific to 2nd list item
                        Toast.makeText(getContext(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
                    }

                    else if(position == 2) {

                        Toast.makeText(getContext(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
                    }
                    else if(position == 3) {

                        Toast.makeText(getContext(),"Place Your Forth Option Code",Toast.LENGTH_SHORT).show();
                    }
                    else if(position == 4) {

                        Toast.makeText(getContext(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        return view;
    }

}