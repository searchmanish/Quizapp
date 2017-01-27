package com.quizapp.quizbox;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_UserCategory extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_fragment__user_category, container, false);
        ImageButton btnBeginner=(ImageButton) rootview.findViewById(R.id.beginner);

        btnBeginner.setOnClickListener(new View.OnClickListener() {

            private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Fragment fragment = new Fragment_course();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentplace, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ImageButton btnExpert=(ImageButton) rootview.findViewById(R.id.expert);

        btnExpert.setOnClickListener(new View.OnClickListener() {
            private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Fragment fragment = new Fragment_courseEx();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentplace, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootview;
    }

}
