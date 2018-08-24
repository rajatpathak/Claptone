package com.appentus.claptone.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.claptone.R;
import com.appentus.claptone.model.Model;

import java.util.List;

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.MyViewHolder> {

    private List<Model> effectList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        public CardView card_view;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            imageView = (ImageView) view.findViewById(R.id.imgView);
            card_view = (CardView) view.findViewById(R.id.card_view);

        }
    }


    public EffectAdapter(List<Model> effectList) {
        this.effectList = effectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.effect_list_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Model movie = effectList.get(position);
        holder.title.setText(movie.getTitle());
        if (holder.title.getText().equals("No Effect")){
            holder.imageView.setImageResource(R.drawable.no_effect);
            holder.title.setText("");

        }

    }

    @Override
    public int getItemCount() {
        return effectList.size();
    }

} //adapter class end



