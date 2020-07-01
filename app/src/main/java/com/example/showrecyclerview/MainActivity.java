package com.example.showrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.showrecyclerview.adapters.GridViewAdapter;
import com.example.showrecyclerview.adapters.ListViewAdapter;
import com.example.showrecyclerview.adapters.RecyclerViewBaseAdapter;
import com.example.showrecyclerview.beans.Datas;
import com.example.showrecyclerview.beans.ItemBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 总结
 * 首先要有控件RecyclerView,添加依赖包
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recycleView;
    private List<ItemBean> mData;
    private RecyclerViewBaseAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到控件
        recycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mSwipeRefreshLayout = findViewById(R.id.refresh_layout);
        //准备数据
        initData();
        //设置默认的显示样式为ListView的效果
        showList(true, false);

        //处理下拉刷新
        handleDownPullUpdate();

    }

    /**
     * 处理下拉刷新
     */
    private void handleDownPullUpdate() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里执行刷新数据的操作
                /**
                 * 当我们在顶部下拉的时候，这个方法就会触发
                 * 但是这是MainThread主线程，不可以执行耗时操作
                 * 一般来说，我们需要请求数据再开一个线程去处理
                 */
                //添加数据
                ItemBean data = new ItemBean();
                data.title = "我是新添加的数据";
                data.icon = R.mipmap.pic_02;
                mData.add(0, data);
                //更新UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //这里要做两件事，一件是让刷新停止，一件是要更新列表
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //这里处理点击事件
                Toast.makeText(MainActivity.this, "您点击了是第" + position + "个条目", Toast.LENGTH_SHORT).show();
            }
        });
        //这里面去处理上拉加载更多
        if (mAdapter instanceof ListViewAdapter) {
            ((ListViewAdapter) mAdapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(final ListViewAdapter.LoaderMoreHolder loaderMoreHolder) {
                    //这里加载更多的数据，同样需要在子线程中完成
                    //更新UI
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Random random = new Random();
                            if(random.nextInt()%2==0){
                                //添加数据
                                ItemBean data = new ItemBean();
                                data.title = "我是新添加的数据...";
                                data.icon = R.mipmap.pic_02;
                                mData.add(data);
                                //这里要做两件事，一件是让刷新停止，一件是要更新列表
                                mAdapter.notifyDataSetChanged();
                                loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_NORMAL);
                            }else{
                                loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_RELOAD);
                            }
                        }
                    }, 3000);
                }
            });
        }
    }

    /**
     * 用以初始化模拟数据
     */
    private void initData() {
        //List<> --> Adapter --> setAdapter -->显示数据
        //创建数据集合
        mData = new ArrayList<>();
        //创建模拟数据
        for (int i = 0; i < Datas.icons.length; i++) {
            //创建数对象
            ItemBean data = new ItemBean();
            data.icon = Datas.icons[i];
            data.title = "我是第" + i + "个条目";
            mData.add(data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            //ListView部分
            case R.id.list_item_vertical_stander:
                showList(true, false);
                Log.d(TAG, "点击了ListView里的垂直标准");
                break;
            case R.id.list_item_vertical_reverse:
                showList(true, true);
                break;
            case R.id.list_item_horizontal_stander:
                showList(false, false);
                break;
            case R.id.list_item_horizontal_reverse:
                showList(false, true);
                break;

            //GridView部分
            case R.id.grid_item_vertical_stander:
                showGrid(true, false);
                break;
            case R.id.grid_item_vertical_reverse:
                showGrid(true, true);
                break;
            case R.id.grid_item_horizontal_stander:
                showGrid(false, false);
                break;
            case R.id.grid_item_horizontal_reverse:
                showGrid(false, true);
                break;

            //瀑布流部分
            case R.id.stagger_item_vertical_stander:
                showStagger(true, false);
                break;
            case R.id.stagger_item_vertical_reverse:
                showStagger(true, true);
                break;
            case R.id.stagger_item_horizontal_stander:
                showStagger(false, false);
                break;
            case R.id.stagger_item_horizontal_reverse:
                showStagger(false, true);
                break;
            //多种条目类型
            case R.id.multi_type:
                //跳到一个新的Activity去实现这个功能
                Intent intent = new Intent(MainActivity.this, MultiTypeActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 用于实现ListView一样的效果
     */
    private void showList(boolean isVertical, boolean isReverse) {
        //RecyclerView需要设置样式，其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器来控制
        //设置水平还是垂直
        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        layoutManager.setReverseLayout(isReverse);
        recycleView.setLayoutManager(layoutManager);
        //创建适配器
        mAdapter = new ListViewAdapter(mData);
        //设置到RecyclerView里
        recycleView.setAdapter(mAdapter);
        //初始化事件
        initListener();
    }

    /**
     * 用来实现GridView一样的效果
     */
    private void showGrid(boolean isVertical, boolean isReverse) {
        //创建布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        //设置水平还是垂直
        gridLayoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
        //设置标准还是反向
        gridLayoutManager.setReverseLayout(isReverse);

        //设置布局管理器
        recycleView.setLayoutManager(gridLayoutManager);
        //创建适配器
        mAdapter = new GridViewAdapter(mData);
        //设置适配器
        recycleView.setAdapter(mAdapter);
        //初始化事件
        initListener();
    }

    /**
     * 实现瀑布流效果
     */
    private void showStagger(boolean isVertical, boolean isReverse) {
        //创建布局管理器,设置水平还是垂直
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        //设置标准还是反向
        staggeredGridLayoutManager.setReverseLayout(isReverse);
        //设置布局管理器
        recycleView.setLayoutManager(staggeredGridLayoutManager);
        //创建适配器
        mAdapter = new GridViewAdapter(mData);
        //设置适配器
        recycleView.setAdapter(mAdapter);
        //初始化事件
        initListener();
    }
}