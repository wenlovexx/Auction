package com.example.wangyouwen.auction;

import com.example.wangyouwen.auction.base.FragmentActivity;

import android.app.Fragment;

public class ViewBid extends FragmentActivity
{
	@Override
	protected Fragment getFragment()
	{
		return new ViewBidFragment();
	}
}
