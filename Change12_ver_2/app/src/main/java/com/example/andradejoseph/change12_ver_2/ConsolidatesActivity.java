package com.example.andradejoseph.change12_ver_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.andradejoseph.change12_ver_2.custom.BaseActivity;
import com.example.andradejoseph.change12_ver_2.model.Crime;
import com.example.andradejoseph.change12_ver_2.model.CrimeLab;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.sessions.SessionManager;
import com.example.andradejoseph.change12_ver_2.utils.DrawerActivity;



import java.util.List;

public class ConsolidatesActivity extends BaseActivity implements Callback{

    private FloatingSearchView mSearchView;
    private View mHeaderView;
    private View mDimSearchViewBackground;
    private ColorDrawable mDimDrawable;


    Crime _crime = null;

    Disciple _disciple = null;

    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";


    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ConsolidatesActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    public ConsolidatesActivity() {
        // Required empty public constructor
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidates);
        DrawerActivity.getInstance().DrawerInit(ConsolidatesActivity.this);
        DrawerActivity.getInstance().setSelection(2);
//
//        _crime = new Crime();
//        _crime.setTitle("Hello Karen Claire <3");
//        CrimeLab.get(this).addCrime(_crime);
//
//        CrimeLab crimeLab = CrimeLab.get(this);
//        List<Crime> crimes = crimeLab.getCrimes();
//        Toast.makeText(this, "aha", Toast.LENGTH_SHORT).show();
//        for(Crime crime: crimes) {
//            Log.d("HALA:  ", crime.getTitle() + " " + crime.getId());
//        }

        _disciple = new Disciple();
        _disciple.setFirst_name("Joseph");
        _disciple.setFull_name("Joseph C. Andrade II");
        _disciple.setNick_name("Joseph C. Andrade II");
        _disciple.setLast_name("Joseph C. Andrade II");
        _disciple.setMiddle_name("Joseph C. Andrade II");


        DiscpleLab.get(this).addDisciple(_disciple);


        DiscpleLab discpleLab = DiscpleLab.get(this);
        List<Disciple> disciples = discpleLab.getDiciples();
        Toast.makeText(this, "aha", Toast.LENGTH_SHORT).show();
        for(Disciple disciple: disciples) {
            Log.d("HALA:  ", disciple.getFirst_name() + " " + disciple.getLast_name());
        }
        init();




    }

    @Override
    public void init() {
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);

        mDimSearchViewBackground = findViewById(R.id.dim_background);
        mDimDrawable = new ColorDrawable(Color.BLACK);
        mDimDrawable.setAlpha(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDimSearchViewBackground.setBackground(mDimDrawable);
        }else {
            mDimSearchViewBackground.setBackgroundDrawable(mDimDrawable);
        }

        mSearchView.setOnLeftMenuClickListener(
                new FloatingSearchView.OnLeftMenuClickListener() {
                    @Override
                    public void onMenuOpened() {
                        DrawerActivity.getInstance().openDrawer();
                    }
                    @Override
                    public void onMenuClosed() {
                        DrawerActivity.getInstance().closeDraweer();
                    }
                } );
    }


    @Override
    public void onMethodCallback(int position) {
        switch(position) {
            case CHANGE_12_MODULE:
                Intent i2 = Change12ManualActivity.newIntent(ConsolidatesActivity.this);
                startActivity(i2);
                finish();
                break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            DrawerActivity.getInstance().openDrawer();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i2 = Change12ManualActivity.newIntent(ConsolidatesActivity.this);
        startActivity(i2);
        finish();
    }
}
