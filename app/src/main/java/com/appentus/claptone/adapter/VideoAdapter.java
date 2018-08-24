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
import com.appentus.claptone.model.VideoModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    Context context;
    private List<VideoModel> videoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,title1,title2,title3;
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/futur_exbold.ttf");
        public ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);

            title3 = (TextView) view.findViewById(R.id.subtitle);
            imageView= (ImageView) view.findViewById(R.id.img1);
//            title1.setTypeface(typeface);
//            title2.setTypeface(typeface);
        }
    }


    public VideoAdapter(List<VideoModel> videoList, Context context) {
        this.videoList = videoList;
        this.context= context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        String path=videoList.get(position).getTitle();
        holder.title.setText(""+videoList.get(position).getTitle());
        holder.title3.setText("Paris-Roissy Chorles de Goulle");
        holder.title3.setSelected(true);  // Set focus to the textview

        Glide.with(context)
                .load(path) // or URI/path
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
