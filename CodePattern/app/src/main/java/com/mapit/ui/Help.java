package com.mapit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapit.R;
import com.mapit.custom.CustomFragment;

/**
 * The Class Help is the Fragment class that is launched when the user
 * clicks on Help option in Left navigation drawer and it simply shows a
 * dummy Help text. You can customize this to display actual Help text.
 */
public class Help extends CustomFragment
{

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.about, null);

		return v;
	}

}
