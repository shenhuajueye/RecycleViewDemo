package com.example.showrecyclerview.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.example.showrecyclerview.R;
import com.example.showrecyclerview.beans.ItemBean;

import java.util.List;

public class StaggerGridAdapter extends RecyclerViewBaseAdapter{
    public StaggerGridAdapter(List<ItemBean> data){
        super(data);
    }
    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_stagger_grid, null);
        return view;
    }
}
