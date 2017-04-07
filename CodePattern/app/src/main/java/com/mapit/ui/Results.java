package com.mapit.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.mapit.DetailActivity;
import com.mapit.R;
import com.mapit.custom.CustomFragment;

/**
 * The Class Results is the Fragment class that shows dummy results data for
 * routes. You can customize this class to load and display actual results and
 * other data.
 */
public class Results extends CustomFragment
{

	/** The list of suggested routes. */
	private ArrayList<String[]> listSuggested;

	/** The list of bus routes. */
	private ArrayList<String[]> listBus;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.results, null);

		setTouchNClick(v.findViewById(R.id.btnTime));
		setTouchNClick(v.findViewById(R.id.v1));
		setTouchNClick(v.findViewById(R.id.v2));
		setTouchNClick(v.findViewById(R.id.v3));

		setHasOptionsMenu(true);

		setupList(v);
		return v;
	}

	/* (non-Javadoc)
	 * @see com.mapit.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.v1 || v.getId() == R.id.v2
				|| v.getId() == R.id.v3)
		{
			startActivity(new Intent(getActivity(), DetailActivity.class)
					.putExtra("detail", true));
		}
	}

	/**
	 * Setup the list view for results.
	 * 
	 * @param v
	 *            the root view
	 */
	private void setupList(View v)
	{
		listBus = loadDummyResults(false);
		listSuggested = loadDummyResults(true);

		ExpandableListView list = (ExpandableListView) v
				.findViewById(R.id.elist);
		ResultAdapter adp = new ResultAdapter();
		list.setAdapter(adp);

		list.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id)
			{
				startActivity(new Intent(getActivity(), DetailActivity.class)
						.putExtra("detail", true));
				return true;
			}
		});

		list.expandGroup(0, false);
		list.expandGroup(1, false);
	}

	/**
	 * Load dummy results for routes. You need to write your own logic to load
	 * the actual list for routes as per your needs.
	 * 
	 * @param suggested
	 *            true if we need to load suggested routes
	 * @return the array list of routes
	 */
	private ArrayList<String[]> loadDummyResults(boolean suggested)
	{
		ArrayList<String[]> al = new ArrayList<String[]>();
		if (suggested)
		{
			al.add(new String[] { "46", null, "$4.10", "40" });
			al.add(new String[] { "23", "34", "$2.4	0", "21" });
			al.add(new String[] { "28", "74", "$8.30", "58" });
		}
		else
		{
			al.add(new String[] { "46", "33", "$4.10", "40" });
			al.add(new String[] { "23", "34", "$2.4	0", "21" });
			al.add(new String[] { "107", "102", "$8.30", "58" });
			al.add(new String[] { "46", "33", "$4.10", "40" });
			al.add(new String[] { "23", "34", "$2.4	0", "21" });
			al.add(new String[] { "107", "102", "$8.30", "58" });
		}
		return al;
	}

	/**
	 * The Class ResultAdapter is the adapter class for routes ListView. The
	 * currently implementation of this adapter simply display static dummy
	 * contents. You need to write the code for displaying actual contents.
	 */
	private class ResultAdapter extends BaseExpandableListAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChild(int, int)
		 */
		@Override
		public String[] getChild(int groupPosition, int childPosition)
		{
			if (groupPosition == 0)
				return listSuggested.get(childPosition);
			return listBus.get(childPosition);
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition)
		{
			return childPosition;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent)
		{
			String s[] = getChild(groupPosition, childPosition);

			if (convertView == null)
				convertView = getLayoutInflater(null).inflate(
						R.layout.result_item_child, null);
			TextView lbl = (TextView) convertView.findViewById(R.id.lbl1);
			lbl.setText(s[0]);

			lbl = (TextView) convertView.findViewById(R.id.lbl2);
			if (s[1] == null)
			{
				lbl.setVisibility(View.GONE);
				convertView.findViewById(R.id.dot).setVisibility(View.GONE);
			}
			else
			{
				lbl.setVisibility(View.VISIBLE);
				convertView.findViewById(R.id.dot).setVisibility(View.VISIBLE);
				lbl.setText(s[1]);
				lbl.setCompoundDrawablesWithIntrinsicBounds(
						groupPosition == 0 ? R.drawable.ic_train
								: R.drawable.ic_bus, 0, 0, 0);
			}

			lbl = (TextView) convertView.findViewById(R.id.lbl3);
			lbl.setText(s[2]);

			lbl = (TextView) convertView.findViewById(R.id.lbl4);
			lbl.setText(s[3]);

			return convertView;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
		 */
		@Override
		public int getChildrenCount(int groupPosition)
		{
			if (groupPosition == 0)
				return listSuggested.size();
			return listBus.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroup(int)
		 */
		@Override
		public String getGroup(int groupPosition)
		{
			if (groupPosition == 0)
				return "Suggested Journeys";
			return "More: Bus only";
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupCount()
		 */
		@Override
		public int getGroupCount()
		{
			return 2;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupId(int)
		 */
		@Override
		public long getGroupId(int groupPosition)
		{
			return groupPosition;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent)
		{
			if (convertView == null)
				convertView = getLayoutInflater(null).inflate(
						R.layout.result_item_grp, null);
			convertView.setClickable(true);
			TextView lbl = (TextView) convertView;
			lbl.setText(getGroup(groupPosition));
			return convertView;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#hasStableIds()
		 */
		@Override
		public boolean hasStableIds()
		{
			return false;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition)
		{
			return true;
		}

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.result, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
}
