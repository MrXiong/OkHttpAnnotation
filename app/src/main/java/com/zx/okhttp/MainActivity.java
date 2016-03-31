package com.zx.okhttp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.zx.okhttp.requestimpl.GetGamesRequest;
import com.zx.okhttp.result.NewGameResponse;
import com.zx.okhttp.result.model.GameInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.toolsfinal.StringUtils;

public class MainActivity extends BaseActivity {

    @Bind(R.id.ptrrv_list)
    PullToRefreshRecyclerView mPtrrvList;
    List<GameInfo> mGameList;
    private boolean mDirection;
    private int mPage = 1;
    private SuperPtrFrameLayoutAdapter mSuperPtrFrameLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGameList = new ArrayList<>();
        mSuperPtrFrameLayoutAdapter = new SuperPtrFrameLayoutAdapter();
        mSuperPtrFrameLayoutAdapter.setList(mGameList);
        mPtrrvList.setAdapter(mSuperPtrFrameLayoutAdapter);
        //首次自动加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrrvList.setRefreshing(true);
                getGames();
            }
        },1000);

        initPullView();
    }

    private void initPullView() {
        mPtrrvList.setSwipeEnable(true);
        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(this, mPtrrvList.getRecyclerView());
        loadMoreView.setLoadmoreString("加载更多");
        loadMoreView.setLoadMorePadding(100);
        mPtrrvList.setLayoutManager(new LinearLayoutManager(this));
        mPtrrvList.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                mDirection = false;
                getGames();
            }
        });
        mPtrrvList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDirection = true;
                getGames();
            }
        });
        mPtrrvList.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mPtrrvList.setLoadMoreFooter(loadMoreView);
        mPtrrvList.onFinishLoading(true, false);
    }

    private void getGames() {
        if(mDirection) {
            mPage = 1;
        } else {
            mPage ++;
        }
        GetGamesRequest request = new GetGamesRequest<NewGameResponse>(){
            @Override
            public void onStart() {
                super.onStart();
                //请求之前
            }

            @Override
            public void onFinish() {
                super.onFinish();
                //请求之后
                    mPtrrvList.setOnRefreshComplete();
                    mPtrrvList.setOnLoadMoreComplete();
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                //请求错误
                Toast.makeText(getBaseContext(), "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLogicSuccess(NewGameResponse newGameResponse) {
                List<GameInfo> data = newGameResponse.getData();
                if (data != null) {
                    if(mDirection) {
                        mGameList.clear();
                    }
                    mGameList.addAll(data);
                    mSuperPtrFrameLayoutAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getBaseContext(), newGameResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLogicFailure(NewGameResponse newGameResponse) {
                super.onLogicFailure(newGameResponse);
                //请求成功，逻辑错误
                String msg = newGameResponse.getMsg();
                if (StringUtils.isEmpty(msg)) {
                    msg = "网络异常";
                }
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }

        };
        GetGamesRequest.GetGamesParam param = new GetGamesRequest.GetGamesParam();
        param.setPage(mPage);
        param.setLimit(8);
        request.setParam(param);
        request.get();

    };
}
