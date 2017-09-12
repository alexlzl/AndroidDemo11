package com.weeho.petim.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.weeho.petim.hxim.HxHelper;
import com.weeho.petim.modle.DaoMaster;
import com.weeho.petim.modle.DaoSession;
import com.weeho.petim.util.ConstantsUtil;

/**
 * Created by wangkui on 2017/4/21.
 */

public class MyApplication extends MultiDexApplication {
    public static Context applicationContext;
    private static MyApplication instance;
    public static boolean debugToggle = true;
    public static int totalNum = 0;
    //授权码
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";
    private static DaoSession daoSession;
    public static IWXAPI mWxApi;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = MyApplication.this;
        //init demo helper
        HxHelper.getInstance().init(instance);
        //初始化数据库对象
        setupDatabase();
        //注册微信
        registToWX();
    }
    private void registToWX() {

        mWxApi = WXAPIFactory.createWXAPI(this, ConstantsUtil.APP_ID, true);
        // 将该app注册到微信
        mWxApi.registerApp( ConstantsUtil.APP_ID);
    }
    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库petIm.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "petIm.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
