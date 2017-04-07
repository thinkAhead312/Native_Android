package com.mapit.ui;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mapit.R;
import com.mapit.custom.CustomFragment;
import com.mapit.model.Data;

/**
 * The Class RouteDetail is the Fragment class that is launched when the user
 * select a route and it simply shows the dummy route on Google map and it also
 * shows a dummy list for navigation data. You can customize this to display
 * actual contents.
 */
public class RouteDetail extends CustomFragment
{

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
		View v = inflater.inflate(R.layout.route_detail, null);

		setHasOptionsMenu(true);

		setupMap(v, savedInstanceState);

		setupList(v);
		return v;
	}

	/**
	 * Setup list of dummy navigation data.
	 * 
	 * @param v
	 *            the root view
	 */
	private void setupList(View v)
	{
		ArrayList<Data> al = loadDummyData();

		LinearLayout vList = (LinearLayout) v.findViewById(R.id.vList);
		for (Data d : al)
		{
			View i = getLayoutInflater(null)
					.inflate(R.layout.detail_item, null);

			TextView lbl = (TextView) i.findViewById(R.id.lbl1);
			lbl.setText(d.getTitle1());

			lbl = (TextView) i.findViewById(R.id.lbl2);
			lbl.setText(d.getDesc());

			lbl = (TextView) i.findViewById(R.id.lbl3);
			lbl.setText(d.getTitle2());

			ImageView img = (ImageView) i.findViewById(R.id.img);
			img.setImageResource(d.getImage1());
			vList.addView(i);
		}
	}

	/**
	 * Load dummy list of navigation data. You need to customize this method to
	 * write your own logic for loading actual list of navigation data.
	 * 
	 * @return the array list of navigation data
	 */
	private ArrayList<Data> loadDummyData()
	{
		ArrayList<Data> al = new ArrayList<Data>();
		al.add(new Data("Walk", "Go to platform", "45 min", R.drawable.ic_walk));
		al.add(new Data("Bray Street", "Northerm- Leaving 2,3,7 min", "10 min",
				R.drawable.ic_flag));
		al.add(new Data("Pulteny Street", "Leaving in 12 min", "12 min",
				R.drawable.ic_flag));
		al.add(new Data("Mc Road", "Leaving in 4,5 min", "11 min",
				R.drawable.ic_flag));
		al.add(new Data("Walk", "Go to platform", "45 min", R.drawable.ic_walk));
		al.add(new Data("Bray Street", "Northerm- Leaving 2,3,7 min", "10 min",
				R.drawable.ic_flag));
		al.add(new Data("Pulteny Street", "Leaving in 12 min", "12 min",
				R.drawable.ic_flag));
		al.add(new Data("Mc Road", "Leaving in 4,5 min", "11 min",
				R.drawable.ic_flag));

		return al;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.refresh, menu);
		super.onCreateOptionsMenu(menu, inflater);
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
			setupMarkersAndRoute();
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
	 * This method simply place a few dummy location markers on Map View and it
	 * also draws a route using dummy locations. You can write your own logic
	 * for loading the locations and placing the marker for each location and
	 * drawing the required root as per your need.
	 */
	private void setupMarkersAndRoute()
	{
		mMap.clear();
		PolylineOptions po = new PolylineOptions();
		LatLng ll[] = { new LatLng(-33.8600, 151.2111),
				new LatLng(-33.8620, 151.2111), new LatLng(-33.8620, 151.2131),
				new LatLng(-33.8610, 151.2131) };
		String title[] = { "Start", "Turn", "", "End" };
		for (int i = 0; i < ll.length; i++)
		{
			if (i != 2)
			{
				MarkerOptions opt = new MarkerOptions();
				opt.position(ll[i]).title(title[i]).snippet("");
				opt.icon(BitmapDescriptorFactory
						.defaultMarker(i == 0 || i == 3 ? BitmapDescriptorFactory.HUE_BLUE
								: BitmapDescriptorFactory.HUE_GREEN));
				mMap.addMarker(opt);
			}
			po.add(ll[i]);
		}

		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll[3], 16));

		mMap.addPolyline(po.color(Color.BLUE).width(5));
	}
}
