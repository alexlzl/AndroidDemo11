package com.weeho.petim.fragment;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.weeho.petim.R;
import com.weeho.petim.RetorfitWapper.ApiCallUtil;
import com.weeho.petim.adapter.AchievementAdapter;
import com.weeho.petim.adapter.SegmentAdapter;
import com.weeho.petim.base.BaseFragment;
import com.weeho.petim.hxim.Constant;
import com.weeho.petim.lib.utils.SharedPreferencesUtil;
import com.weeho.petim.lib.utils.StringUtil;
import com.weeho.petim.lib.utils.WeakHandler;
import com.weeho.petim.modle.AcheveBaseBean;
import com.weeho.petim.modle.AcheveBaseBean.ResultBean;
import com.weeho.petim.modle.LoginBeanSave;
import com.weeho.petim.modle.PetDanBaseBean;
import com.weeho.petim.modle.PetDanBaseBean.ResultBean.*;
import com.weeho.petim.network.ApiHttpCilent;
import com.weeho.petim.network.Utils;
import com.weeho.petim.util.ConstantsUtil;
import com.weeho.petim.util.ToastUtil;
import com.weeho.petim.view.AlertDanDialog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by wangkui on 2017/4/18.
 * 成就界面
 */

public class AchievementFragment extends BaseFragment {

    private RecyclerView recycleView;
    private RecyclerView dan_recyclerview;
    private FragmentActivity mActivity;
    private String userid;
    private AcheveBaseBean acheveBaseBean;
    private MyHandler handler = new MyHandler(this);
    private TextView ex_notice;
    private TextView tv_achieve_update;
    private AchievementAdapter achievementAdapter;
    ArrayList<ResultBean.DataBean>  list = new ArrayList<>();
    private ApiCallUtil mApiCallUtil;
    private PetDanBaseBean mPetDanBaseBean;
    private SegmentAdapter mSegmentAdapter;
    //段位集合
    private List<ListBean> mListResultBean = new ArrayList<>();
    private TextView dan_name;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, rootView);
//        initData();
//        return rootView;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }




    public class MyHandler extends WeakHandler<AchievementFragment> {
        public MyHandler(AchievementFragment reference) {
            super(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.CONTENT_SUCCESS:
                    if (acheveBaseBean != null && acheveBaseBean.getResult() != null && acheveBaseBean.getResult().getData() != null) {
                        list.clear();
                        list = (ArrayList<ResultBean.DataBean>) acheveBaseBean.getResult().getData();
                        int size = list.size();
                        if (size > 0) {
                            tv_achieve_update.setText("(已改正" + size + "项恶习)");
                            achievementAdapter.setData(list);
                            achievementAdapter.notifyDataSetChanged();
                            ex_notice.setVisibility(View.GONE);
                            recycleView.setVisibility(View.VISIBLE);
                        } else {
                            ex_notice.setVisibility(View.VISIBLE);
                            recycleView.setVisibility(View.GONE);
                        }
                    }
                    break;
                case ConstantsUtil.CONTENT_SUCCESS_TWO:
                    //获取宠物段位成功
                    GetDanSuccess();

                    break;
                case ConstantsUtil.CONTENT_FAIL:
                    //
                    String error = (String) msg.obj;
                    if(StringUtil.isEmpty(error))
                        ToastUtil.showToast(getReference().mActivity, "获取失败,请稍后重试");
                    else
                        ToastUtil.showToast(getReference().mActivity, error);
                    break;
            }
        }
    }

    /**
     * 获取等级名称
     */
    private String mSegmentName = "";
    private  List<String> reach = new ArrayList<>();
    private  void getDanName(List<ListBean> mListResultBean){
        reach.clear();
        for(ListBean listBean:mListResultBean){
            if(listBean.isIsReach()){
                reach.add(listBean.getName());
            }
        }
    }
    private void GetTitleName(){
        if(reach.contains("王者")){
            mSegmentName = "王者";
            return;
        }else if(reach.contains("钻石")){
            mSegmentName = "钻石";
            return;
        }else if(reach.contains("铂金")){
            mSegmentName = "铂金";
            return;
        }else if(reach.contains("黄金")){
            mSegmentName = "黄金";
            return;
        }else if(reach.contains("白银")){
            mSegmentName = "白银";
            return;
        }else if(reach.contains("青铜")){
            mSegmentName = "青铜";
            return;
        }
    }
    private void GetDanSuccess() {
        if(mPetDanBaseBean != null && mPetDanBaseBean.getResult()!= null && mPetDanBaseBean.getResult().getList()!= null){
            mListResultBean = mPetDanBaseBean.getResult().getList();
            if(mListResultBean != null) {
               getDanName(mListResultBean);
                GetTitleName();
                if (!StringUtil.isEmpty(mSegmentName))
                    dan_name.setText("(已达成" + mSegmentName + "等级)");
                mSegmentAdapter.setData((ArrayList<ListBean>) mListResultBean);
                mSegmentAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 更新宠物段位试图
     * */
    private void UpdateView(boolean isReach,ImageView iv){
        if(isReach)
            iv.setImageResource(R.drawable.achievemented);
        else
            iv.setImageResource(R.drawable.achievement);
    }
    @Override
    protected boolean isShowLeftIcon() {
        return false;
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.achieve_view, container,
                true);
        initView(rootView);
        initData();
        return rootView;
    }

    private void initView(View rootView) {
        mActivity = getActivity();
        dan_recyclerview = (RecyclerView) rootView.findViewById(R.id.dan_recyclerview);
        recycleView = (RecyclerView) rootView.findViewById(R.id.id_recyclerview);
        tv_achieve_update = (TextView) rootView.findViewById(R.id.tv_achieve_update);
        dan_name = (TextView) rootView.findViewById(R.id.dan_name);
        ex_notice = (TextView) rootView.findViewById(R.id.ex_notice);
        StaggeredGridLayoutManager stagger = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager stagger_dan = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(stagger);
        dan_recyclerview.setLayoutManager(stagger_dan);
    }

    private void Dimess() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (ApiHttpCilent.loading != null && ApiHttpCilent.loading.isShowing())
                    ApiHttpCilent.loading.dismiss();
            }
        });
    }

    private String GetUserId() {
        LoginBeanSave loginBeanSave = (LoginBeanSave) SharedPreferencesUtil.get(getActivity(), "login", "login");
        return loginBeanSave.getUserId();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(!Utils.isNetworkConnected(getActivity()))
                return;
            GetAchieveData();
            GetPetDan();
//            Gson gson = new Gson();
//            mPetDanBaseBean = gson.fromJson(getJson("json.txt"),PetDanBaseBean.class);
//            GetDanSuccess();
        }
    }
    private void initData() {
        Bundle bundle = getArguments();
        userid = (String) bundle.get("userid");
        //获取宠物信息
        if (StringUtil.isEmpty(userid))
            userid = GetUserId();
        mApiCallUtil = ApiCallUtil.getInstant(getActivity());
        achievementAdapter = new AchievementAdapter(list, mActivity);
        recycleView.setAdapter(achievementAdapter);

        mSegmentAdapter = new SegmentAdapter(mListResultBean, mActivity);
        dan_recyclerview.setAdapter(mSegmentAdapter);
        //宠物成就
        GetAchieveData();
        //宠物段位
        GetPetDan();
    }
    public String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = mActivity.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    /**
     * 获取宠物段位信息
     * */
    private void GetPetDan(){
        ApiHttpCilent.CreateLoading(mActivity);

        mApiCallUtil.GetPetDan(userid).enqueue(new Callback<PetDanBaseBean>() {
            @Override
            public void onResponse(Call<PetDanBaseBean> call, Response<PetDanBaseBean> response) {
                Dimess();
                mPetDanBaseBean = response.body();
                if (mPetDanBaseBean == null)
                    return;
                Message message = Message.obtain();
                if ("1".equals(mPetDanBaseBean.getStatus())) {
                    message.what = ConstantsUtil.CONTENT_SUCCESS_TWO;
                } else {
                    message.what = ConstantsUtil.CONTENT_FAIL;// 错误
                    message.obj = mPetDanBaseBean.getError().getInfo();
                }
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<PetDanBaseBean> call, Throwable t) {
                Dimess();
                Message message = Message.obtain();
                message.what = ConstantsUtil.CONTENT_FAIL;// 错误
                message.obj = "数据错误,请稍后重试";
                handler.sendMessage(message);
            }
        });
    }
    private void GetAchieveData() {
        ApiHttpCilent.CreateLoading(mActivity);

        mApiCallUtil.GetAchievement(userid).enqueue(new Callback<AcheveBaseBean>() {
            @Override
            public void onResponse(Call<AcheveBaseBean> call, Response<AcheveBaseBean> response) {
                Dimess();
                Message message = Message.obtain();
                acheveBaseBean = response.body();
                if (acheveBaseBean == null) {
                    message.what = ConstantsUtil.CONTENT_FAIL;// 错误
                    message.obj = "数据错误,请稍后重试";
                    handler.sendMessage(message);
                    return;
                }
                if ("1".equals(acheveBaseBean.getStatus())) {
                    message.what = ConstantsUtil.CONTENT_SUCCESS;
                } else {
                    message.what = ConstantsUtil.CONTENT_FAIL;// 错误
                    message.obj = acheveBaseBean.getError().getInfo();
                }
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<AcheveBaseBean> call, Throwable t) {
                Dimess();
                Message message = Message.obtain();
                message.what = ConstantsUtil.CONTENT_FAIL;// 错误
                message.obj = "数据错误,请稍后重试";
                handler.sendMessage(message);
            }
        });
    }






    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void getNetData() {

    }

    @Override
    protected void setViewListener() {
    }


    @Override
    protected boolean hasTitle() {
        return true;
    }

    @Override
    protected boolean hasTitleIcon() {
        return false;
    }

    @Override
    protected boolean hasDownIcon() {
        return false;
    }

    @Override
    protected void reloadCallback() {

    }

    @Override
    protected String setTitleName() {
        return "成就";
    }

    @Override
    protected String setRightText() {
        return null;
    }

    @Override
    protected int setLeftImageResource() {
        return 0;
    }

    @Override
    protected int setMiddleImageResource() {
        return 0;
    }

    @Override
    protected int setRightImageResource() {
        return 0;
    }
}

