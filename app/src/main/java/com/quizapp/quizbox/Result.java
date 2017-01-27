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
public class Result extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_result, container, false);
        ImageButton button=(ImageButton) rootview.findViewById(R.id.startanother);

        button.setOnClickListener(new View.OnClickListener() {

            private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                Fragment fragment = new Fragment_UserCategory();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentplace, fragment);
                transaction.commit();
            }
        });

        return rootview;
    }

}
