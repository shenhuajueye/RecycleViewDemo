package com.example.showrecyclerview.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.showrecyclerview.R;
import com.example.showrecyclerview.beans.MultiTypeBean;

import java.util.List;

public class MultiTypeAdapter extends RecyclerView.Adapter {
    //定义三个常量标识，因为我们有三种类型
    public static final int TYPE_FULL_IMAGE = 0;
    public static final int TYPE_RIGHT_IMAGE = 1;
    public static final int TYPE_THREE_IMAGES = 2;

    private final List<MultiTypeBean> mData;

    public MultiTypeAdapter(List<MultiTypeBean> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //这里去创建ViewHolder
        if(viewType == TYPE_FULL_IMAGE){
            view = View.inflate(parent.getContext(), R.layout.item_type_full_image,null);
            return new FullImageHolder(view);
        }else if(viewType == TYPE_RIGHT_IMAGE){
            view = View.inflate(parent.getContext(), R.layout.item_type_right_title_image,null);
            return new RightImageHolder(view);
        }else{
            view = View.inflate(parent.getContext(), R.layout.item_type_three_image,null);
            return new ThreeImagesHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    //复写一个方法来返回条目类型，来返回条目类型
    @Override
    public int getItemViewType(int position) {
        MultiTypeBean multiTypeBean = mData.get(position);
        if (multiTypeBean.type == 0) {
            return TYPE_FULL_IMAGE;
        } else if (multiTypeBean.type == 1) {
            return TYPE_RIGHT_IMAGE;
        }else{
            return TYPE_THREE_IMAGES;
        }
    }

    public class FullImageHolder extends RecyclerView.ViewHolder{

        public FullImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class RightImageHolder extends RecyclerView.ViewHolder{

        public RightImageHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ThreeImagesHolder extends RecyclerView.ViewHolder{

        public ThreeImagesHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
