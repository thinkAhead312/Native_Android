package com.example.andradejoseph.change12_ver_2.custom;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.andradejoseph.change12_ver_2.R;
import com.example.andradejoseph.change12_ver_2.model.Change12Lab;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.utils.C4DbHelperFunctions;

import java.util.ArrayList;
import java.util.List;


public class CustomFragment extends Fragment implements OnClickListener
{
    private Toolbar mToolbar;




    /**
     * Set the touch and click listener for a View.
     *
     * @param v
     *            the view
     * @return the same view
     */

    /**
     * Initialize Fragment View
     * @param view
     */
    public void init(View view)
    {
        mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);

    }

    /**
     * (non-Javadoc)
     * @see OnClickListener#onClick(View)
     */
    @Override
    public void onClick(View v)
    {

    }
}
