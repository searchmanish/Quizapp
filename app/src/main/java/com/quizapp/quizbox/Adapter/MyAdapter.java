package com.quizapp.quizbox.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizapp.quizbox.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 18-01-2017.
 */

public class MyAdapter extends BaseAdapter {
    ArrayList<SingleRow> list;
    Context context;

    public MyAdapter(Context c) {
        context = c;
        list = new ArrayList<SingleRow>();

        Resources resources = c.getResources();
        String[] titles = resources.getStringArray(R.array.titles);
        String[] description = resources.getStringArray(R.array.description);
        int[] images = {R.drawable.im1, R.drawable.im2, R.drawable.im3, R.drawable.im4, R.drawable.im5,R.drawable.im6,R.drawable.im7,
               R.drawable.im8,R.drawable.im9,R.drawable.im10,R.drawable.im11};
          // int []images={R.drawable.im1};
        for (int i = 0; i <11; i++) {
            list.add(new SingleRow(titles[i], description[i], images[i]));
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        MyViewHolder holder=null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row, viewGroup, false);
            holder=new MyViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder=(MyViewHolder) row.getTag();
        }
        // TextView titles = (TextView) row.findViewById(R.id.textView);
        //TextView description = (TextView) row.findViewById(R.id.textView2);
        //ImageView images = (ImageView) row.findViewById(R.id.imageView);

        SingleRow temp = list.get(i);

        holder.titles.setText(temp.titles);
        holder.description.setText(temp.description);
        holder.images.setImageResource(temp.images);

        return row;
    }
}


class SingleRow {
    String titles;
    String description;
    int images;

    SingleRow(String titles, String description, int images) {
        this.titles = titles;
        this.description = description;
        this.images = images;
    }
}

class MyViewHolder
{
    ImageView images;
    TextView titles;
    TextView description;

    MyViewHolder(View view)
    {
        images= (ImageView) view.findViewById(R.id.imageView);
        titles= (TextView) view.findViewById(R.id.textView);
        description= (TextView) view.findViewById(R.id.textView2);
    }
}
