package com.example.andradejoseph.change12_ver_2.utils;

import android.content.Context;
import android.util.Log;

import com.example.andradejoseph.change12_ver_2.constants.Constants;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Change12Lab;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.sessions.SessionManager;

import java.util.List;

/**
 * Created by ANDRADEJOSEPH on 4/5/2017.
 */

public class C4DbHelperFunctions {

    Context mContext = null;
    SessionManager sessionManager = null;
    public C4DbHelperFunctions(Context context) {
        mContext = context;
        sessionManager = new SessionManager(mContext);

    }

    public  boolean can_edit_and_delete_user(final String discipleId) {
        final String userId = sessionManager.userPref.getString(Constants.USER_ID, null);

        final DiscpleLab discpleLab = DiscpleLab.get(mContext);
                try {
                    Disciple discipleDetails = discpleLab.getDisciple(discipleId);
                    String disciplerId = discipleDetails.getDiscipler();
                    if (discipleId.trim().equals(userId.trim()) || disciplerId.trim().equals(userId)) {
                        return true;
                    } else if (disciplerId != null || !disciplerId.equals("")) {
                        can_edit_and_delete_user(disciplerId);
                    }

                } catch (NullPointerException e) {
                    return false;
                }
        return false;
    }

    public int countMyAllConsolidates() {
        final DiscpleLab discpleLab = DiscpleLab.get(mContext);
        final Change12Lab change12Lab = Change12Lab.get(mContext);

        final List<Changee> changeeList = change12Lab.getChangee();


        final int[] myTotalConsolidatesCount = {0};
        new Thread() {
            @Override
            public void run() {
                for (Changee changee : changeeList) {
                    final Disciple disciple = discpleLab.getDisciple(changee.getChangee().trim());
                    boolean is_leader = can_edit_and_delete_user(disciple.getDiscipler());
                    if (is_leader) {
                        myTotalConsolidatesCount[0]++;
                    }

                }
            }
        }.start();

        return myTotalConsolidatesCount[0];
    }

}
