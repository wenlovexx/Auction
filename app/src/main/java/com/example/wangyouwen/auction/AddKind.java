package com.example.wangyouwen.auction;

import com.example.wangyouwen.auction.base.FragmentActivity;

import android.app.Fragment;

public class AddKind extends FragmentActivity
{
	@Override
	public Fragment getFragment()
	{
		return new AddKindFragment();
	}
}
