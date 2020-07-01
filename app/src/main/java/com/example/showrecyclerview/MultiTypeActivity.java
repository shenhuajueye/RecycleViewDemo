package com.example.showrecyclerview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.showrecyclerview.adapters.MultiTypeAdapter;
import com.example.showrecyclerview.beans.Datas;
import com.example.showrecyclerview.beans.MultiTypeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiTypeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<MultiTypeBean> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        mRecyclerView = (RecyclerView) findViewById(R.id.multi_type_list);

        //准备数据
        mData = new ArrayList<>();

        initDatas();
        //创建和设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //创建适配器
        MultiTypeAdapter adapter = new MultiTypeAdapter(mData);
        //设置适配器
        mRecyclerView.setAdapter(adapter);

    }

    private void initDatas() {
        Random random = new Random();
        for(int i = 0; i< Datas.icons.length; i++){
            MultiTypeBean data = new MultiTypeBean();
            data.pic = Datas.icons[i];
            data.type = random.nextInt(3);
            mData.add(data);

        }
    }
}
