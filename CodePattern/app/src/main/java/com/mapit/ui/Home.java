package com.mapit.ui;

import java.util.ArrayList;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapit.DetailActivity;
import com.mapit.R;
import com.mapit.custom.CustomFragment;

/**
 * The Class Home is the Fragment class that is launched when the user clicks on
 * Home option in Left navigation drawer and this is also the default fragment
 * for MainActivity class. It simply shows a dummy list of saved places and also
 * includes a Google Map view. You need to write your own code to load and
 * display actual contents.
 */
public class Home extends CustomFragment {

	/** The saved places list. */
	private ArrayList<String> placeList;

	/** The tab. */
	private View tab;

	/** The map view. */
	MapView mMapView;

	/** The Google map. */
	private GoogleMap mMap;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.home, null);

		setupMap(v, savedInstanceState);

		setupList(v);

		tab = setTouchNClick(v.findViewById(R.id.tab1));
		tab.setEnabled(false);
		setTouchNClick(v.findViewById(R.id.tab2));
		setTouchNClick(v.findViewById(R.id.tab3));
		setTouchNClick(v.findViewById(R.id.tab4));
		setTouchNClick(v.findViewById(R.id.tab5));
		return v;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		mMapView.onDestroy();
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
		mMapView.getMapAsync((OnMapReadyCallback) getContext());
		if (mMap != null) {
			if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
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
		MapsInitializer.initialize(getActivity());
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

	/**
	 * Setup the list view for Saved places.
	 * 
	 * @param v
	 *            the root view
	 */
	private void setupList(View v)
	{
		loadPlacesList();
		ListView list = (ListView) v.findViewById(R.id.list);

		View h = getLayoutInflater(null).inflate(R.layout.home_list_header,
				null);
		list.addHeaderView(h);

		setTouchNClick(h.findViewById(R.id.btnW1));
		setTouchNClick(h.findViewById(R.id.btnW2));
		setTouchNClick(h.findViewById(R.id.btnW3));

		list.setAdapter(new PlaceAdapter());
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3)
			{
				startActivity(new Intent(getActivity(), DetailActivity.class)
						.putExtra("detail", true));
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.socialshare.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.tab1 || v.getId() == R.id.tab2
				|| v.getId() == R.id.tab3 || v.getId() == R.id.tab4
				|| v.getId() == R.id.tab5)
		{
			tab.setEnabled(true);
			tab = v;
			tab.setEnabled(false);
		}
		else if (v.getId() == R.id.btnW1 || v.getId() == R.id.btnW2
				|| v.getId() == R.id.btnW3)
		{
			startActivity(new Intent(getActivity(), DetailActivity.class));
		}
	}

	/**
	 * This method currently loads a dummy list of places. You can write the
	 * actual implementation of loading places.
	 */
	private void loadPlacesList()
	{
		ArrayList<String> pl = new ArrayList<String>();
		pl.add("Starbucks coffee");
		pl.add("John's hous");
		pl.add("City center metro station");
		pl.add("Public bus depot");
		pl.add("Royal Plaza");
		pl.add("Super market");
		pl.add("Peter's office");

		placeList = new ArrayList<String>(pl);
		placeList.addAll(pl);
		placeList.addAll(pl);

	}

	/**
	 * The Class PlaceAdapter is the adapter class for Places ListView. The
	 * currently implementation of this adapter simply display static dummy
	 * contents. You need to write the code for displaying actual contents.
	 */
	private class PlaceAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return placeList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public String getItem(int arg0)
		{
			return placeList.get(arg0);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2)
		{
			if (v == null)
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.home_item, null);

			TextView lbl = (TextView) v;
			lbl.setText(getItem(pos));

			return v;
		}

	}
}
