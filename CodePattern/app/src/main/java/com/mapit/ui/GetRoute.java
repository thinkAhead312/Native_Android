package com.mapit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapit.DetailActivity;
import com.mapit.R;
import com.mapit.custom.CustomFragment;

/**
 * The Class GetRoute is the Fragment class that is launched when the user
 * clicks on Get Route option in Left navigation drawer. It shows a MapView with
 * options for set Start and End points for route. You need to write your own
 * logic for determining the route and other required information.
 */
public class GetRoute extends CustomFragment
{

	/** The tab. */
	private View tab;

	/** The map view. */
	private MapView mMapView;

	/** The Google map. */
	private GoogleMap mMap;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.get_route, null);

		setupMap(v, savedInstanceState);

		tab = setTouchNClick(v.findViewById(R.id.tab1));
		setTouchNClick(v.findViewById(R.id.tab2));
		setTouchNClick(v.findViewById(R.id.btnRoute));
		setTouchNClick(v.findViewById(R.id.close));

		return v;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause()
	{
		mMapView.onPause();
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy()
	{
		mMapView.onDestroy();
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		mMapView.onResume();

		mMap = mMapView.getMap();
		if (mMap != null)
		{
			mMap.setMyLocationEnabled(true);
			mMap.setInfoWindowAdapter(null);
			setupMarker();
		}
	}

	/**
	 * Setup and initialize the Google map view.
	 * 
	 * @param v
	 *            the root view
	 * @param savedInstanceState
	 *            the saved instance state
	 */
	private void setupMap(View v, Bundle savedInstanceState)
	{
		try
		{
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e)
		{
			e.printStackTrace();
		}
		mMapView = (MapView) v.findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);

	}

	/**
	 * This method simply place a dummy location marker on Map View. You can
	 * write your own logic for loading the locations and placing the marker for
	 * each location as per your need.
	 */
	private void setupMarker()
	{
		mMap.clear();
		LatLng l = new LatLng(-33.8600, 151.2111);
		MarkerOptions opt = new MarkerOptions();
		opt.position(l).title("Coffee House").snippet("Sydney, Australia");
		opt.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));

		mMap.addMarker(opt);
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l, 12));

	}

	/* (non-Javadoc)
	 * @see com.socialshare.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.tab1 || v.getId() == R.id.tab2)
		{
			tab.setEnabled(true);
			tab = v;
			tab.setEnabled(false);
		}
		else if (v.getId() == R.id.close)
		{
			((EditText) getView().findViewById(R.id.txt)).setText(null);
		}
		else if (v.getId() == R.id.btnRoute)
		{
			startActivity(new Intent(getActivity(), DetailActivity.class));
		}
	}

}
