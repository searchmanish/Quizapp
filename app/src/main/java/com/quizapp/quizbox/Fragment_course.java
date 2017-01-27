package com.quizapp.quizbox;


import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.quizapp.quizbox.Adapter.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_course extends Fragment {
    MyAdapter myAdapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.fragment_fragment_course, container, false);
        Context context=rootview.getContext();
        listView=(ListView)rootview.findViewById(R.id.list_view);
        //Button button=(Button) rootview.findViewById(R.id.English);
        myAdapter=new MyAdapter(context);
        listView.setAdapter(myAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),""+i,Toast.LENGTH_LONG).show();
            }
        });

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Fragment_startTimer();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentplace, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });  */

        return rootview;
    }

}
