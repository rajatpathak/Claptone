package com.appentus.claptone.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.claptone.R;
import com.appentus.claptone.model.ShowsModel;

import java.util.List;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.MyViewHolder> {
    Context context;
    private List<ShowsModel> showsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,title1,title2,title3;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/futur_exbold.ttf");
        public ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            title1 = (TextView) view.findViewById(R.id.title1);
            title2 = (TextView) view.findViewById(R.id.title2);
            title3 = (TextView) view.findViewById(R.id.title3);
            title1.setTypeface(typeface);
            title2.setTypeface(typeface);
        }
    }


    public ShowsAdapter(List<ShowsModel> showsList,Context context) {
        this.showsList= showsList;
        this.context= context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shows_list_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ShowsModel showsModel= showsList.get(position);
        holder.title.setText("HR ROOF");
        holder.title1.setText("23th April 18");
        holder.title2.setText("Tuesday");
        holder.title3.setText("Paris-Roissy Chorles de Goulle");
        holder.title3.setSelected(true);  // Set focus to the textview

    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }
}
