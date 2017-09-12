package com.zhy.zhy_xutils_test;

import com.zhy.ioc.view.ViewInjectUtils;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ViewInjectUtils.inject(this);

	}

}
