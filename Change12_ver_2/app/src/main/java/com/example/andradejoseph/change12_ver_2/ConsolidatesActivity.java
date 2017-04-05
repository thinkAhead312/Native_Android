package com.example.andradejoseph.change12_ver_2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.andradejoseph.change12_ver_2.custom.SingleFragmentActivity;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Change12Lab;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.ui.Change12CurrentWaveConsolidates;
import com.example.andradejoseph.change12_ver_2.ui.Change12WaveList;
import com.example.andradejoseph.change12_ver_2.utils.DrawerActivity;

import java.util.List;

public class ConsolidatesActivity extends SingleFragmentActivity implements Callback, AdapterCallback{

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ConsolidatesActivity.class);
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
        init();
        getSupportActionBar().setTitle("Consolidates");
        DrawerActivity.getInstance().DrawerInit(ConsolidatesActivity.this);
        DrawerActivity.getInstance().setSelection(2);


        DiscpleLab discpleLab = DiscpleLab.get(getApplicationContext());
        List<Disciple> disciples = discpleLab.getDiciples();
        for (Disciple disciple: disciples) {
            Log.d("ConsolidatesActivity: ", disciple.getFirst_name() + " " + disciple.getDiscipler());
        }



        Change12Lab change12Lab = Change12Lab.get(getApplicationContext());
        List<Change12> change12s = change12Lab.getChange12();
        for(Change12 change12: change12s) {
            Log.d("ConsolidatesActivity: ", change12.getChange12_id() + " " + change12.getWave_num());
        }


        List<Changee> changees = change12Lab.getChangee();
        for(Changee changee: changees) {
            Log.d("ConsolidatesActivity> ", changee.getChange_12() + " " + changee.getChangee());
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


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent i2 = Change12ManualActivity.newIntent(ConsolidatesActivity.this);
//        startActivity(i2);
//        finish();
//    }

    @Override
    public void onWaveItemCallback(View mChangeWaveNum, String waveNum, String waveDate) {
//        Toast.makeText(this, "Karen <3", Toast.LENGTH_SHORT).show();

        String cardTransitionName = "";
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment  = manager.findFragmentById(R.id.frame_fragment);

        fragment = new Change12CurrentWaveConsolidates();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            transaction.addSharedElement(mChangeWaveNum, ViewCompat.getTransitionName(mChangeWaveNum));
//        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementReturnTransition(TransitionInflater.from(
                   ConsolidatesActivity.this).inflateTransition(R.transition.simple_fragment_transition));
            fragment.setExitTransition(TransitionInflater.from(
                    ConsolidatesActivity.this).inflateTransition(android.R.transition.fade));

            fragment.setSharedElementEnterTransition(TransitionInflater.from(
                    ConsolidatesActivity.this).inflateTransition(R.transition.simple_fragment_transition));
            fragment.setEnterTransition(TransitionInflater.from(
                    ConsolidatesActivity.this).inflateTransition(android.R.transition.fade));

            cardTransitionName = mChangeWaveNum.getTransitionName();
            transaction.addSharedElement(mChangeWaveNum, cardTransitionName);
        }



        Bundle bundle = new Bundle();
        bundle.putString("TRANS_NAME", cardTransitionName);
        bundle.putString("WAVE_NUM", waveNum);
        bundle.putString("WAVE_DATE", waveDate);
        fragment.setArguments(bundle);

        transaction.replace(R.id.frame_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onPopStack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment  = manager.findFragmentById(R.id.frame_fragment);
        FragmentTransaction transaction = manager.beginTransaction();


        fragment = new Change12WaveList();
        transaction.replace(R.id.frame_fragment, fragment);
        transaction.commit();

    }
}
