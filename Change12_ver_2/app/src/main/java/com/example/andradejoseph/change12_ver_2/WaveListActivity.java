package com.example.andradejoseph.change12_ver_2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.andradejoseph.change12_ver_2.custom.SingleFragmentActivity;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Change12Lab;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.ui.Change12WaveList;
import com.example.andradejoseph.change12_ver_2.utils.DrawerActivity;

import java.util.List;

public class WaveListActivity extends SingleFragmentActivity implements Callback{

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, WaveListActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return new Change12WaveList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DrawerActivity.getInstance().DrawerInit(WaveListActivity.this);
        DrawerActivity.getInstance().setSelection(2);
    }

    @Override
    public void onMethodCallback(int position) {
        switch(position) {
            case CHANGE_12_MODULE:
                Intent i2 = Change12ManualActivity.newIntent(WaveListActivity.this);
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
    }
}
