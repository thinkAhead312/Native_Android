package com.mapit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapit.R;
import com.mapit.custom.CustomFragment;

/**
 * The Class SelectCity is the Fragment class that is launched when the user
 * clicks on Select City option in Left navigation drawer and it simply shows
 * two cities to select. You can customize this to display actual contents.
 */
public class SelectCity extends CustomFragment
{

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.sel_city, null);

		setTouchNClick(v.findViewById(R.id.btn1));
		setTouchNClick(v.findViewById(R.id.btn2));
		return v;
	}

	/* (non-Javadoc)
	 * @see com.mapit.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.btn1 || v.getId() == R.id.btn2)
		{
			getView().findViewById(
					v.getId() == R.id.btn1 ? R.id.btn2 : R.id.btn1).setEnabled(
					true);
			v.setEnabled(false);
		}
	}

}
