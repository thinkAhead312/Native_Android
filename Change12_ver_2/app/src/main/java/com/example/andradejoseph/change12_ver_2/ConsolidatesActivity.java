package com.example.andradejoseph.change12_ver_2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;
import com.example.andradejoseph.change12_ver_2.custom.SingleFragmentActivity;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.ui.LessonsFragment;
import com.example.andradejoseph.change12_ver_2.utils.DrawerActivity;

import java.util.ArrayList;
import java.util.List;

public class ConsolidatesActivity extends SingleFragmentActivity implements Callback{

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ConsolidatesActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return new LessonsFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
        getSupportActionBar().setTitle("Consolidates");
        DrawerActivity.getInstance().DrawerInit(ConsolidatesActivity.this);
        DrawerActivity.getInstance().setSelection(2);

        DiscpleLab discpleLab = DiscpleLab.get(getApplicationContext());
        List<Change12> change12s = discpleLab.getChange12();
        for(Change12 change12: change12s) {
            Log.d("ConsolidatesActivity: ", change12.getChange12_id() + " " + change12.getWave_num());
        }
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
