package com.appentus.claptone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appentus.claptone.R;
import com.appentus.claptone.adapter.ShowsAdapter;
import com.appentus.claptone.model.ShowsModel;

import java.util.ArrayList;
import java.util.List;

public class ShowsFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private ShowsAdapter mAdapter;
    private List<ShowsModel> showsList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tab_fragment_2, container, false);
        recyIniti();
        return view;

    }


    public void recyIniti() {


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new ShowsAdapter(showsList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        prepareMovieData();

    }


    public void prepareMovieData() {
        ShowsModel model = new ShowsModel("No Effect");
        showsList.add(model);

        model = new ShowsModel("Effect 1");
        showsList.add(model);


        model = new ShowsModel("Effect 2");
        showsList.add(model);


        model = new ShowsModel("Effect 3");
        showsList.add(model);


        model = new ShowsModel("Effect 4");
        showsList.add(model);


        model = new ShowsModel("Effect 5");
        showsList.add(model);


        model = new ShowsModel("Effect 6");
        showsList.add(model);


        model = new ShowsModel("Effect 7");
        showsList.add(model);


        model = new ShowsModel("Effect 8");
        showsList.add(model);


        mAdapter.notifyDataSetChanged();
    }

}