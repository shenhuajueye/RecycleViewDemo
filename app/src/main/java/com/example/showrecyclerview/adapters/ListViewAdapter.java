package com.example.showrecyclerview.adapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.showrecyclerview.R;
import com.example.showrecyclerview.beans.ItemBean;

import java.util.List;

public class ListViewAdapter extends RecyclerViewBaseAdapter {
    //普通条目类型
    public static final int TYPE_NORMAL = 0;
    //加载更多
    public static final int TYPE_LOADER_MORE = 1;

    public OnRefreshListener mUpPullRefreshListener;

    public ListViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        if (viewType == TYPE_NORMAL) {
            return new InnerHolder(view);
        } else {
            return new LoaderMoreHolder(view);
        }
    }

    /**
     * 用于绑定holder,一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL && holder instanceof InnerHolder) {
            //在这里设置数据
            ((InnerHolder) holder).setData(mData.get(position), position);
        } else if (getItemViewType(position) == TYPE_LOADER_MORE && holder instanceof LoaderMoreHolder) {
            ((LoaderMoreHolder) holder).update(LoaderMoreHolder.LOADER_STATE_LOADING);
        }
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view;
        //根据类型来创建view
        if (viewType == TYPE_NORMAL) {
            view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_load_more, null);
        }
        return view;
    }


    @Override
    public int getItemViewType(int position) {

        if (position == getItemCount() - 1) {
            //返回加载更多
            return TYPE_LOADER_MORE;
        }
        return TYPE_NORMAL;
    }

    /**
     * 设置刷新的监听接口
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mUpPullRefreshListener = listener;
    }

    //定义接口
    public interface OnRefreshListener {
        void onUpPullRefresh(LoaderMoreHolder loaderMoreHolder);
    }

    public class LoaderMoreHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "LoaderMoreHolder";
        private LinearLayout mLoading;
        private TextView mReload;
        public static final int LOADER_STATE_LOADING = 0;
        public static final int LOADER_STATE_RELOAD = 1;
        public static final int LOADER_STATE_NORMAL = 2;

        public LoaderMoreHolder(@NonNull View itemView) {
            super(itemView);
            mLoading = (LinearLayout) itemView.findViewById(R.id.loading);
            mReload = (TextView) itemView.findViewById(R.id.reload);
            mReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //需要点击触发加载数据
                    update(LOADER_STATE_LOADING);
                }
            });
        }

        public void update(int state) {
            //重置控件的状态
            mLoading.setVisibility(View.GONE);
            mReload.setVisibility(View.GONE);
            switch (state) {
                case LOADER_STATE_LOADING:
                    mLoading.setVisibility(View.VISIBLE);
                    //触发加载数据
                    startLoaderMore();
                    break;
                case LOADER_STATE_RELOAD:
                    mReload.setVisibility(View.VISIBLE);
                    //触发加载数据
                    break;
                case LOADER_STATE_NORMAL:
                    mLoading.setVisibility(View.GONE);
                    mReload.setVisibility(View.GONE);
                    break;
            }
        }

        private void startLoaderMore() {
            if(mUpPullRefreshListener!=null){
                mUpPullRefreshListener.onUpPullRefresh(this);
            }
        }
    }
}
