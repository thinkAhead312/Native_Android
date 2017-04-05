package com.example.andradejoseph.change12_ver_2.custom;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.Fade;

import com.example.andradejoseph.change12_ver_2.R;

/**
 * Created by ANDRADEJOSEPH on 3/23/2017.
 */

public abstract class SingleFragmentActivity extends BaseActivity{

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidates);
//        setupWindowAnimations();


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frame_fragment);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.frame_fragment, fragment)
                    .commit();
        }
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(500);
            getWindow().setEnterTransition(fade);
        }
    }

}
