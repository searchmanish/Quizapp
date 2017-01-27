package com.quizapp.quizbox;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.quizapp.quizbox.Adapter.MyAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_courseEx extends Fragment {
    MyAdapter myAdapter;
    ListView listView1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_fragment_course_ex, container, false);
        listView1= (ListView) rootview.findViewById(R.id.list_view1);
        //Button button=(Button) rootview.findViewById(R.id.computer);
        myAdapter=new MyAdapter(getActivity());
        listView1.setAdapter(myAdapter);

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Fragment_startTimer();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentplace, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });    */

        return rootview;
    }
}
